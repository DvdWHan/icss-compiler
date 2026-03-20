package nl.han.ica.icss.ast.expression.math.operation;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.math.MathExpression;
import nl.han.ica.icss.ast.expression.math.UnaryExpression;

@EqualsAndHashCode(callSuper = true)
public class UnaryMinus extends UnaryExpression {
  public UnaryMinus(MathExpression operand) {
    super(operand);
  }

  @Override
  public int evaluate() {
    return -getOperand().evaluate();
  }
}
