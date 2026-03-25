package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.checker.SemanticError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for all nodes in the Abstract Syntax Tree (AST) of the ICSS compiler.
 * <p>Each node maintains a reference to its parent and a list of child nodes,
 * forming a mutable tree structure. Nodes can be added, removed, and replaced
 * while preserving parent-child relationships.</p>
 * <p>The generic {@code Self} type follows the Curiously Recurring Template Pattern (CRTP),
 * allowing fluent APIs to return the concrete subtype instead of {@code AstNode}.</p>
 * <p>Subclasses typically represent concrete syntactic constructs and may expose
 * typed accessors for specific child node types.</p>
 *
 * @param <Self> the concrete subtype of this node
 * @see #addChild(AstNode)
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AstNode<Self extends AstNode<Self>> {
  @Getter private AstNode<?> parent;
  @EqualsAndHashCode.Include private final List<AstNode<?>> children = new ArrayList<>();
  @Getter private SemanticError error;

  /**
   * Returns an unmodifiable view of this node's children.
   *
   * @return immutable list of child nodes
   */
  public final List<AstNode<?>> getChildren() {
    return Collections.unmodifiableList(children);
  }

  /**
   * Returns all children of the specified type.
   * <p>Intended for use by composite nodes to expose typed accessors.</p>
   *
   * @param clazz the type to filter on
   * @param <T> the child node type
   * @return list of children of the given type
   * @see Stylesheet#getRulesets()
   */
  protected <T extends AstNode<?>> List<T> getChildrenOfType(Class<T> clazz) {
    return children.stream().filter(clazz::isInstance).map(clazz::cast).toList();
  }

  /**
   * Adds a child to this node.
   * <p>The child's parent is set to this node. If {@code child} is {@code null},
   * the call is ignored.</p>
   *
   * @param child the node to add
   * @return this node, typed as {@code Self} for fluent usage
   */
  @SuppressWarnings("unchecked")
  public final Self addChild(AstNode<?> child) {
    if (child != null) {
      child.parent = this;
      children.add(child);
    }
    return (Self)this;
  }

  /**
   * Inserts a child at the specified index.
   * <p>The child's parent is set to this node. If {@code child} is {@code null},
   * the call is ignored.</p>
   *
   * @param index position at which to insert
   * @param child the node to add
   */
  public final void addChild(int index, AstNode<?> child) {
    if (child != null) {
      child.parent = this;
      children.add(index, child);
    }
  }

  /**
   * Removes this node from its parent.
   * <p>After removal, this node becomes detached from the tree. The reference
   * from the parent is cleared; the resulting orphaned node is eligible for
   * garbage collection if no other references exist.</p>
   *
   * @throws IllegalStateException if this node has no parent
   */
  public final void removeSelf() {
    if (parent == null) {
      throw new IllegalStateException(error("remove", this, "node has no parent"));
    }
    parent.removeChild(this);
  }

  private void removeChild(AstNode<?> child) {
    children.remove(child);
  }

  /**
   * Replaces this node with another node in the parent.
   * <p>The new node takes this node's position in the parent's child list.
   * This node becomes detached from the tree and may be garbage collected
   * if no longer referenced.</p>
   *
   * @param node the replacement node (must not be {@code null})
   * @throws IllegalStateException if this node has no parent
   */
  public final void replaceWith(AstNode<?> node) {
    if (parent == null) {
      throw new IllegalStateException(error("replace", this, "node has no parent"));
    }
    if (node == null) {
      throw new IllegalArgumentException(error("replace", this, "replacement node is null"));
    }
    parent.replaceChild(this, node);
  }

  private void replaceChild(AstNode<?> oldChild, AstNode<?> newChild) {
    if (newChild == null) {
      throw new IllegalArgumentException(error("replaceChild", oldChild, "replacement node is null"));
    }
    if (!children.contains(oldChild)) {
      throw new IllegalArgumentException(error(
          "replaceChild",
          oldChild,
          "node is not a child of parent",
          "parent=%s".formatted(parent)
      ));
    }
    newChild.parent = this;
    children.set(children.indexOf(oldChild), newChild);
  }

  private static String error(String action, AstNode<?> failedFor, String because) {
    return "%s failed for %s: %s".formatted(action, failedFor.getNodeLabel(), because);
  }

  @SuppressWarnings("SameParameterValue")
  private static String error(String action, AstNode<?> failedFor, String because, String context) {
    return "%s failed for %s: %s{%s}".formatted(action, failedFor.getNodeLabel(), because, context);
  }

  /**
   * Returns a label used to represent this node.
   * <p>Defaults to the simple class name. Subclasses may override to provide
   * additional context, typically formatted as:
   * {@code ClassName(additional-info)}.</p>
   *
   * @return display label for this node
   */
  public String getNodeLabel() {
    return getClass().getSimpleName();
  }

  /**
   * Indicates whether this node has an associated semantic error.
   *
   * @return {@code true} if an error is present, otherwise {@code false}
   */
  public final boolean hasError() {
    return error != null;
  }

  /**
   * Attaches a semantic error to this node.
   * <p>Used during semantic analysis to mark invalid constructs. Consumers
   * (e.g. GUI) may use this information to highlight the node.</p>
   *
   * @param description error description
   */
  public final void setError(String description) {
    this.error = new SemanticError(description);
  }

  /**
   * Returns a formatted, indented string representation of this node and its subtree.
   * <p>The output is a recursive tree structure suitable for debugging and inspection.</p>
   *
   * @return string representation of the AST subtree rooted at this node
   */
  @Override
  public final String toString() {
    var sb = new StringBuilder();
    toString(this, sb, 0);
    return sb.toString();
  }

  private static void toString(AstNode<?> node, StringBuilder sb, int indentation) {
    indent(sb, indentation).append(node.getNodeLabel());
    if (node.getChildren().isEmpty()) {
      return;
    }
    sb.append("[\n");
    List<AstNode<?>> children = node.getChildren();
    for (int i = 0; i < children.size(); ++i) {
      AstNode<?> child = children.get(i);
      toString(child, sb, indentation + 1);
      if (i < children.size() - 1) {
        sb.append(",");
      }
      sb.append("\n");
    }
    indent(sb, indentation).append("]");
  }

  private static StringBuilder indent(StringBuilder sb, int indentation) {
    return sb.append("\t".repeat(indentation));
  }
}
