package nl.han.ica.icss.ast.variable;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.Expression;

@EqualsAndHashCode(callSuper = true)
public class VariableIdentifier extends Expression {
  private final String identifier;

  public VariableIdentifier(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public String getNodeLabel() {
    return "%s(%s)".formatted(getClass().getSimpleName(), identifier);
  }
}
