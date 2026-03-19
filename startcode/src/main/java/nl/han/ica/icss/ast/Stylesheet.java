package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
public class Stylesheet implements AstNode {
  private final List<AstNode> variableAssignments = new ArrayList<>();
  private final List<AstNode> rulesets = new ArrayList<>();

  @Override
  public List<AstNode> getChildren() {
    List<AstNode> children = new ArrayList<>();
    children.addAll(variableAssignments);
    children.addAll(rulesets);
    return Collections.unmodifiableList(children);
  }

  @Override
  public AstNode addChild(AstNode child) {
    if (child instanceof VariableAssignment variableAssignment) {
      variableAssignments.add(variableAssignment);
    } else if (child instanceof Ruleset ruleset) {
      rulesets.add(ruleset);
    }
    return this;
  }

  @Override
  public String toString() {
    return AstNode.toString(this);
  }
}
