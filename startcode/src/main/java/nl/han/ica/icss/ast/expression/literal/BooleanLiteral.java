package nl.han.ica.icss.ast.expression.literal;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.Literal;

@EqualsAndHashCode(callSuper = true)
public class BooleanLiteral extends Literal<Boolean> {
  public BooleanLiteral(Boolean value) {
    super(value);
  }

  @Override
  public String getValueString() {
    return "%b".formatted(getValue()).toUpperCase();
  }

  @Override
  public Type getType() {
    return Type.BOOLEAN;
  }
}
