package nl.han.ica.icss.ast.expression.math.unary;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.MathExpression;
import nl.han.ica.icss.ast.expression.math.UnaryExpression;
import nl.han.ica.icss.ast.expression.math.Value;

@EqualsAndHashCode(callSuper = true)
public class UnaryPlus extends UnaryExpression {
  public UnaryPlus(MathExpression operand) {
    super(operand);
  }

  @Override
  public Value<?> evaluate(Value<?> operand) {
    return new Value<>(operand.type(), +asInt(operand));
  }
}
