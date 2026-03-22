package nl.han.ica.icss.ast.expression.math;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.AstNode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public abstract class BinaryExpression extends MathExpression {
  @Getter private MathExpression left;
  @Getter private MathExpression right;

  public BinaryExpression(MathExpression left, MathExpression right) {
    left.setParent(this);
    right.setParent(this);
    this.left = left;
    this.right = right;
  }

  @Override
  public List<AstNode> getChildren() {
    return List.of(left, right);
  }

  @Override
  public AstNode addChild(AstNode child) {
    child.setParent(this);
    if (child instanceof MathExpression mathExpression) {
      if (left == null) {
        this.left = mathExpression;
      } else if (right == null) {
        this.right = mathExpression;
      }
    }
    return this;
  }

  @Override
  public void removeChild(AstNode child) {
    if (child instanceof MathExpression mathExpression) {
      if (left == mathExpression) {
        left = null;
      } else if (right == mathExpression) {
        right = null;
      }
    }
  }
}
