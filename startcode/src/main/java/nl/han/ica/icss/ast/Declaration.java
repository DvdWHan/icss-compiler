package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.expression.Expression;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class Declaration extends AstNode {
  private Property property = null;
  private Expression expression = null;

  public Declaration(Property property, Expression expression) {
    this.property = property;
    this.expression = expression;
  }

  @Override
  public List<AstNode> getChildren() {
    return List.of(property, expression);
  }

  @Override
  @SuppressWarnings("PatternVariableHidesField")
  public AstNode addChild(AstNode child) {
    if (child instanceof Property property) {
      this.property = property;
    } else if (child instanceof Expression expression) {
      this.expression = expression;
    }
    return this;
  }
}
