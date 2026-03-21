package nl.han.ica.icss.ast.expression.math;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.AstNode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public abstract class BinaryExpression extends MathExpression {
  @Getter private final MathExpression left;
  @Getter private final MathExpression right;

  public BinaryExpression(MathExpression left, MathExpression right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public List<AstNode> getChildren() {
    return List.of(left, right);
  }
}
