package nl.han.ica.icss.ast.literal;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class BooleanLiteral extends Literal {
  public BooleanLiteral(String stringValue) {
    super(stringValue);
  }
}
