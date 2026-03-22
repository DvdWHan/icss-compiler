package nl.han.ica.icss.ast.expression.math.binary;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.MathExpression;
import nl.han.ica.icss.ast.expression.math.BinaryExpression;
import nl.han.ica.icss.ast.expression.math.Value;

@EqualsAndHashCode(callSuper = true)
public class BinarySubtraction extends BinaryExpression {
  public BinarySubtraction(MathExpression left, MathExpression right) {
    super(left, right);
  }

  @Override
  public Value<?> evaluate(Value<?> left, Value<?> right) {
    return new Value<>(left.type(), asInt(left) - asInt(right));
  }
}
