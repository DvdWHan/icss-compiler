package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Stylesheet extends AstNode {
  @Getter private final List<VariableAssignment> variableAssignments = new ArrayList<>();
  @Getter private final List<Ruleset> rulesets = new ArrayList<>();

  public void addVariableAssignment(VariableAssignment variableAssignment) {
    variableAssignments.add(variableAssignment);
    addChild(variableAssignment);
  }

  public void addRuleset(Ruleset ruleset) {
    rulesets.add(ruleset);
    addChild(ruleset);
  }
}
