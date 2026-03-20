package nl.han.ica.icss.ast.expression.math.operation;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.math.BinaryExpression;
import nl.han.ica.icss.ast.expression.math.MathExpression;

@EqualsAndHashCode(callSuper = true)
public class BinaryAddition extends BinaryExpression {
  public BinaryAddition(MathExpression left, MathExpression right) {
    super(left, right);
  }

  @Override
  public int evaluate() {
    return getLeft().evaluate() + getRight().evaluate();
  }
}
