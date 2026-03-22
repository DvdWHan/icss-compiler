package nl.han.ica.icss.ast.expression.math;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.expression.MathExpression;

@EqualsAndHashCode(callSuper = true)
public abstract class BinaryExpression extends MathExpression {
  @Getter private MathExpression left;
  @Getter private MathExpression right;

  public BinaryExpression(MathExpression left, MathExpression right) {
    this.left = left;
    this.right = right;
    addChild(left);
    addChild(right);
  }

  public abstract Value<?> evaluate(Value<?> left, Value<?> right);

  @Override
  public Value<?> evaluate() {
    return evaluate(left.evaluate(), right.evaluate());
  }
}
