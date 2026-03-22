package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nl.han.ica.icss.ast.expression.VariableIdentifier;

@EqualsAndHashCode(callSuper = true)
public class VariableAssignment extends AstNode {
  private final VariableIdentifier identifier;
  @Getter @Setter private Expression expression;

  public VariableAssignment(VariableIdentifier identifier, Expression expression) {
    this.identifier = identifier;
    this.expression = expression;
    addChild(identifier);
    addChild(expression);
  }

  public String getIdentifier() {
    return identifier.getIdentifier();
  }

  @Override
  public String getNodeLabel() {
    return "%s(%s=%s)".formatted(getClass().getSimpleName(), identifier.getNodeLabel(), expression.getNodeLabel());
  }
}
