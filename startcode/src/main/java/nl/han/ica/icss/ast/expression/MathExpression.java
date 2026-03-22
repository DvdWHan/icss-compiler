package nl.han.ica.icss.ast.expression;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.expression.math.Value;

@EqualsAndHashCode(callSuper = true)
public abstract class MathExpression extends Expression {
  public abstract Value<?> evaluate();

  protected int asInt(Value<?> value) {
    return (int) value.value();
  }

  @Override
  public Type getType() {
    return Type.UNDEFINED;
  }
}
