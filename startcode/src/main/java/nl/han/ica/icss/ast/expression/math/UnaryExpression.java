package nl.han.ica.icss.ast.expression.math;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.AstNode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public abstract class UnaryExpression extends MathExpression {
  @Getter private MathExpression operand;

  public UnaryExpression(MathExpression operand) {
    operand.setParent(this);
    this.operand = operand;
  }

  @Override
  public List<AstNode> getChildren() {
    return List.of(operand);
  }

  @Override
  public AstNode addChild(AstNode child) {
    child.setParent(this);
    if (child instanceof UnaryExpression unaryExpression) {
      this.operand = unaryExpression;
    }
    return this;
  }

  @Override
  public void removeChild(AstNode child) {
    if (child instanceof MathExpression mathExpression) {
      if (operand == mathExpression) {
        operand = null;
      }
    }
  }
}
