package nl.han.ica.icss.ast.expression.binary;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.expression.BinaryExpression;
import nl.han.ica.icss.ast.expression.Value;

@EqualsAndHashCode(callSuper = true)
public class BinaryAddition extends BinaryExpression {
  public BinaryAddition(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public Value<?> evaluate(Value<?> left, Value<?> right) {
    int value = (int)left.value() + (int)right.value();
    return new Value<>(left.type(), value);
  }
}
