package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
public class Declaration extends AstNode {
  @Getter private final Property property;
  @Getter @Setter private Expression expression;

  public Declaration(Property property, Expression expression) {
    this.property = property;
    this.expression = expression;
    addChild(property);
    addChild(expression);
  }

  @Override
  public String getNodeLabel() {
    return "%s(%s:%s)".formatted(getClass().getSimpleName(), property.getNodeLabel(), expression.getNodeLabel());
  }
}
