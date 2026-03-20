package nl.han.ica.icss.ast.literal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.expression.Expression;

@EqualsAndHashCode(callSuper = true)
public abstract class Literal extends Expression {
  @Getter private final String stringValue;

  public Literal(String stringValue) {
    this.stringValue = stringValue;
  }

  @Override
  public String getNodeLabel() {
    return "%s(%s)".formatted(getClass().getSimpleName(), getStringValue());
  }
}
