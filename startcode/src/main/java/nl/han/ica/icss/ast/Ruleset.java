package nl.han.ica.icss.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Ruleset implements AstNode {
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
    if (child instanceof Selector selector) {
      this.selector = selector;
    } else if (child instanceof VariableAssignment variableAssignment) {
      this.variableAssignments.add(variableAssignment);
    } else if (child instanceof Declaration declaration) {
      this.declarations.add(declaration);
    }
    return this;
  }
}
