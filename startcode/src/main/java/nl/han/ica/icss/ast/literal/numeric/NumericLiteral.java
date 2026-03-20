package nl.han.ica.icss.ast.literal.numeric;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.literal.Literal;

@EqualsAndHashCode(callSuper = true)
public abstract class NumericLiteral extends Literal {
  public NumericLiteral(String stringValue) {
    super(stringValue);
  }
}
