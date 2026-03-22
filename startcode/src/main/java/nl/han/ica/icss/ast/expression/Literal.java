package nl.han.ica.icss.ast.expression;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.Expression;

@EqualsAndHashCode(callSuper = true)
public abstract class Literal<T> extends Expression {
  @Getter private final T value;

  public Literal(T value) {
    this.value = value;
  }

  public abstract String getValueString();

  @Override
  public String getNodeLabel() {
    return "%s(%s)".formatted(getClass().getSimpleName(), getValueString());
  }
}
