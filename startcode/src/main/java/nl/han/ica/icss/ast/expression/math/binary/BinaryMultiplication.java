package nl.han.ica.icss.ast.expression.math.binary;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.MathExpression;
import nl.han.ica.icss.ast.expression.math.BinaryExpression;
import nl.han.ica.icss.ast.expression.math.Value;

@EqualsAndHashCode(callSuper = true)
public class BinaryMultiplication extends BinaryExpression {
  public BinaryMultiplication(MathExpression left, MathExpression right) {
    super(left, right);
  }

  @Override
  public Value<?> evaluate(Value<?> left, Value<?> right) {
    Type resultType = left.type() == Type.SCALAR ? right.type() : left.type();
    return new Value<>(resultType, asInt(left) * asInt(right));
  }
}
