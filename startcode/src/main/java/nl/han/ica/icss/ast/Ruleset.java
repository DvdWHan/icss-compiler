package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.selector.Selector;
import nl.han.ica.icss.ast.variable.VariableAssignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Ruleset extends AstNode {
  private Selector selector = null;
  private final List<AstNode> variableAssignments = new ArrayList<>();
  private final List<AstNode> declarations = new ArrayList<>();

  @Override
  public List<AstNode> getChildren() {
    List<AstNode> children = new ArrayList<>();
    children.add(selector);
    children.addAll(variableAssignments);
    children.addAll(declarations);
    return Collections.unmodifiableList(children);
  }

  @Override
  @SuppressWarnings("PatternVariableHidesField")
  public AstNode addChild(AstNode child) {
    child.setParent(this);
    if (child instanceof Selector selector) {
      this.selector = selector;
    } else if (child instanceof VariableAssignment variableAssignment) {
      this.variableAssignments.add(variableAssignment);
    } else if (child instanceof Declaration declaration) {
      this.declarations.add(declaration);
    }
    return this;
  }

  @Override
  public void removeChild(AstNode child) {
    if (child instanceof Selector) {
      this.selector = null;
    } else if (child instanceof VariableAssignment variableAssignment) {
      variableAssignments.remove(variableAssignment);
    } else if (child instanceof Declaration declaration) {
      declarations.remove(declaration);
    }
  }
}
