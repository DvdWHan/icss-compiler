package nl.han.ica.icss.ast.expression.math;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
public abstract class BinaryExpression extends MathExpression {
  @Getter private final MathExpression left;
  @Getter private final MathExpression right;

  public BinaryExpression(MathExpression left, MathExpression right) {
    this.left = left;
    this.right = right;
  }
}
