package nl.han.ica.icss.ast.literal.numeric;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ScalarLiteral extends NumericLiteral {
  public ScalarLiteral(String stringValue) {
    super(stringValue);
  }
}
