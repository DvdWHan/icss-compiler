package nl.han.ica.icss.ast.expression.unary;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.expression.UnaryExpression;
import nl.han.ica.icss.ast.expression.Value;

@EqualsAndHashCode(callSuper = true)
public class UnaryPlus extends UnaryExpression {
  public UnaryPlus(Expression operand) {
    super(operand);
  }

  @Override
  public Value<?> evaluate(Value<?> operand) {
    int value = (int)operand.value();
    return new Value<>(operand.type(), +value);
  }
}
