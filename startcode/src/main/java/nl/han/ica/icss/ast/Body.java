package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Body extends AstNode {
  public List<VariableAssignment> getVariableAssignments() {
    return getChildrenOfType(VariableAssignment.class);
  }

  public List<Declaration> getDeclarations() {
    return getChildrenOfType(Declaration.class);
  }

  public List<ConditionalStatement> getConditionalStatements() {
    return getChildrenOfType(ConditionalStatement.class);
  }
}
