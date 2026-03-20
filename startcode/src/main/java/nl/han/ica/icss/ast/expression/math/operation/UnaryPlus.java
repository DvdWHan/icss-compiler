package nl.han.ica.icss.ast.expression.math.operation;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.math.MathExpression;
import nl.han.ica.icss.ast.expression.math.UnaryExpression;

@EqualsAndHashCode(callSuper = true)
public class UnaryPlus extends UnaryExpression {
  public UnaryPlus(MathExpression operand) {
    super(operand);
  }

  @Override
  public int evaluate() {
    // just added the '+' for completeness :)
    return +getOperand().evaluate();
  }
}
