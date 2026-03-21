package nl.han.ica.icss.ast.literal.numeric;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PercentageLiteral extends NumericLiteral {
  public PercentageLiteral(String stringValue) {
    super(stringValue, Integer.parseInt(stringValue.substring(0, stringValue.length() - 1)));
  }
}
