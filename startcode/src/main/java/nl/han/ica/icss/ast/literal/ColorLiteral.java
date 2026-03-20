package nl.han.ica.icss.ast.literal;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ColorLiteral extends Literal {
  public ColorLiteral(String stringValue) {
    super(stringValue);
  }
}
