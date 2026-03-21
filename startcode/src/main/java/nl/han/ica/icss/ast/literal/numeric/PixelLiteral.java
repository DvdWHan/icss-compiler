package nl.han.ica.icss.ast.literal.numeric;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PixelLiteral extends NumericLiteral {
  public PixelLiteral(String stringValue) {
    super(stringValue, Integer.parseInt(stringValue.substring(0, stringValue.length() - 2)));
  }
}
