package nl.han.ica.icss.ast.expression.math;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
public abstract class UnaryExpression extends MathExpression {
  @Getter private final MathExpression operand;

  public UnaryExpression(MathExpression operand) {
    this.operand = operand;
  }
}
