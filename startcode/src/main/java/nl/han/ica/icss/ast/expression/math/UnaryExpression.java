package nl.han.ica.icss.ast.expression.math;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.expression.MathExpression;

@EqualsAndHashCode(callSuper = true)
public abstract class UnaryExpression extends MathExpression {
  @Getter private MathExpression operand;

  public UnaryExpression(MathExpression operand) {
    this.operand = operand;
    addChild(operand);
  }

  public abstract Value<?> evaluate(Value<?> operand);

  @Override
  public Value<?> evaluate() {
    return evaluate(operand.evaluate());
  }
}
