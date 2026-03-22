package nl.han.ica.icss.ast.expression.literal;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.Literal;

@EqualsAndHashCode(callSuper = true)
public class ColorLiteral extends Literal<String> {
  public ColorLiteral(String value) {
    super(value);
  }

  @Override
  public String getValueString() {
    return "#%s".formatted(getValue());
  }

  @Override
  public Type getType() {
    return Type.COLOR;
  }
}
