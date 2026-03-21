package nl.han.ica.icss.ast.variable;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.math.MathExpression;

@EqualsAndHashCode(callSuper = true)
public class VariableIdentifier extends MathExpression {
  private final String identifier;

  public VariableIdentifier(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public int evaluate() {
    return 0;
  }

  @Override
  public String getNodeLabel() {
    return "%s(%s)".formatted(getClass().getSimpleName(), identifier);
  }
}
