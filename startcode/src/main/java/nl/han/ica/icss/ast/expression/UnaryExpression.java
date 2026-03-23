package nl.han.ica.icss.ast.expression;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.Expression;

@EqualsAndHashCode(callSuper = true)
public abstract class UnaryExpression extends Expression {
  @Getter private Expression operand;

  public UnaryExpression(Expression operand) {
    this.operand = operand;
    addChild(operand);
  }

  public abstract Value<?> evaluate(Value<?> operand);

  @Override
  public Type getType() {
    return Type.UNDEFINED;
  }
}
