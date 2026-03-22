package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.variable.VariableAssignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Stylesheet extends AstNode {
  private final List<AstNode> variableAssignments = new ArrayList<>();
  private final List<AstNode> rulesets = new ArrayList<>();

  @Override
  public List<AstNode> getChildren() {
    var children = new ArrayList<AstNode>();
    children.addAll(variableAssignments);
    children.addAll(rulesets);
    return Collections.unmodifiableList(children);
  }

  @Override
  public AstNode addChild(AstNode child) {
    child.setParent(this);
    if (child instanceof VariableAssignment variableAssignment) {
      variableAssignments.add(variableAssignment);
    } else if (child instanceof Ruleset ruleset) {
      rulesets.add(ruleset);
    }
    return this;
  }

  @Override
  public void removeChild(AstNode child) {
    if (child instanceof VariableAssignment variableAssignment) {
      variableAssignments.remove(variableAssignment);
    } else if (child instanceof Ruleset ruleset) {
      rulesets.remove(ruleset);
    }
  }
}
