package nl.han.ica.icss.ast.literal.numeric;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.expression.math.MathExpression;

@EqualsAndHashCode(callSuper = true)
public abstract class NumericLiteral extends MathExpression {
  @Getter private final String stringValue;
  private final int computedValue;

  public NumericLiteral(String stringValue, int computedValue) {
    this.stringValue = stringValue;
    this.computedValue = computedValue;
  }

  @Override
  public int evaluate() {
    return this.computedValue;
  }

  @Override
  public String getNodeLabel() {
    return "%s(%s)".formatted(getClass().getSimpleName(), getStringValue());
  }
}
