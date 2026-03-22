package nl.han.ica.icss.ast.expression.literal;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.Literal;

@EqualsAndHashCode(callSuper = true)
public abstract class NumericLiteral extends Literal<Integer> {
  public NumericLiteral(Integer value) {
    super(value);
  }
}
