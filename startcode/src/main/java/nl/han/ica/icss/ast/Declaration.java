package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.expression.Expression;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class Declaration extends AstNode {
  private Property property = null;
  private Expression expression = null;

  @Override
  public List<AstNode> getChildren() {
    return List.of(property, expression);
  }

  @Override
  @SuppressWarnings("PatternVariableHidesField")
  public AstNode addChild(AstNode child) {
    child.setParent(this);
    if (child instanceof Property property) {
      this.property = property;
    } else if (child instanceof Expression expression) {
      this.expression = expression;
    }
    return this;
  }

  @Override
  public void removeChild(AstNode child) {
    if (child instanceof Property) {
      this.property = null;
    } else if (child instanceof Expression) {
      this.expression = null;
    }
  }
}
