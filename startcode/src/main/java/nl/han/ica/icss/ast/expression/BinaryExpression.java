package nl.han.ica.icss.ast.expression;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.Expression;

@EqualsAndHashCode(callSuper = true)
public abstract class BinaryExpression extends Expression {
  @Getter private final Expression left;
  @Getter private final Expression right;

  public BinaryExpression(Expression left, Expression right) {
    this.left = left;
    this.right = right;
    addChild(left);
    addChild(right);
  }

  public abstract Value<?> evaluate(Value<?> left, Value<?> right);

  @Override
  public Type getType() {
    return Type.UNDEFINED;
  }
}
