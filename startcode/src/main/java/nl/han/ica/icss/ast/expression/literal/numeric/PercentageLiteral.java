package nl.han.ica.icss.ast.expression.literal.numeric;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.literal.NumericLiteral;

@EqualsAndHashCode(callSuper = true)
public class PercentageLiteral extends NumericLiteral {
  public PercentageLiteral(int value) {
    super(value);
  }

  @Override
  public String getValueString() {
    return "%d%%".formatted(getValue());
  }

  @Override
  public Type getType() {
    return Type.PERCENTAGE;
  }
}
