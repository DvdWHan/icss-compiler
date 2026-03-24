package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Stylesheet extends AstNode<Stylesheet> {
  public List<VariableAssignment> getVariableAssignments() {
    return getChildrenOfType(VariableAssignment.class);
  }

  public List<Ruleset> getRulesets() {
    return getChildrenOfType(Ruleset.class);
  }
}
