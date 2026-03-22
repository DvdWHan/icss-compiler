package nl.han.ica.icss.ast.expression.literal.numeric;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.literal.NumericLiteral;

@EqualsAndHashCode(callSuper = true)
public class PixelLiteral extends NumericLiteral {
  public PixelLiteral(int value) {
    super(value);
  }

  @Override
  public String getValueString() {
    return "%dpx".formatted(getValue());
  }

  @Override
  public Type getType() {
    return Type.PIXEL;
  }
}
