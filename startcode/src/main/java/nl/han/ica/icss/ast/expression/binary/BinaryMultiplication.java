package nl.han.ica.icss.ast.expression.binary;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.expression.BinaryExpression;
import nl.han.ica.icss.ast.expression.Value;

@EqualsAndHashCode(callSuper = true)
public class BinaryMultiplication extends BinaryExpression {
  public BinaryMultiplication(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public Value<?> evaluate(Value<?> left, Value<?> right) {
    int value = (int)left.value() * (int)right.value();
    Type resultType = left.type() == Type.SCALAR ? right.type() : left.type();
    return new Value<>(resultType, value);
  }
}
