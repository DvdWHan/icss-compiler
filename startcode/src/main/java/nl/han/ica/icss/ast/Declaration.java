package nl.han.ica.icss.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Declaration implements AstNode {
  private Property property = null;
  private Expression expression = null;

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
