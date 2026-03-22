package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.checker.SemanticError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
public abstract class AstNode {
  @Getter private AstNode parent;
  private final List<AstNode> children = new ArrayList<>();
  @Getter private SemanticError error;

  public final List<AstNode> getChildren() {
    return Collections.unmodifiableList(children);
  }

  public final AstNode addChild(AstNode child) {
    if (child != null) {
      child.parent = this;
      children.add(child);
    }
    return this;
  }

  public final void addChild(int index, AstNode child) {
    if (child != null) {
      child.parent = this;
      children.add(index, child);
    }
  }

  public final void remove() {
    if (parent == null) {
      throw new IllegalStateException("Cannot remove %s without a parent".formatted(this));
    }
    parent.removeChild(this);
  }

  public final void removeChild(AstNode child) {
    children.remove(child);
  }

  public final void replaceWith(AstNode node) {
    if (parent == null) {
      throw new IllegalStateException("Cannot replace %s with %s without a parent".formatted(this, node));
    }
    parent.replaceChild(this, node);
  }

  public final void replaceChild(AstNode oldChild, AstNode newChild) {
    if (newChild == null) {
      throw new IllegalStateException("Cannot replace %s with null".formatted(oldChild));
    }
    if (!children.contains(oldChild)) {
      throw new IllegalArgumentException("%s is not a child of %s".formatted(oldChild, newChild));
    }
    newChild.parent = this;
    children.set(children.indexOf(oldChild), newChild);
  }

  public final boolean hasError() {
    return error != null;
  }

  public final void setError(String description) {
    this.error = new SemanticError(description);
  }

  public String getNodeLabel() {
    return getClass().getSimpleName();
  }

  @Override
  public final String toString() {
    var sb = new StringBuilder();
    toString(this, sb, 0);
    return sb.toString();
  }

  private static void toString(AstNode node, StringBuilder sb, int indentation) {
    indent(sb, indentation).append(node.getNodeLabel());
    if (node.getChildren().isEmpty()) {
      return;
    }
    sb.append("[\n");
    List<AstNode> children = node.getChildren();
    for (int i = 0; i < children.size(); ++i) {
      AstNode child = children.get(i);
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
