package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Declaration extends AstNode {
  private Property property = null;
  private Expression expression = null;

  public Declaration(String property) {
    this.property = new Property(property);
  }

  @Override
  public String getNodeLabel() {
    return "Declaration";
  }

  @Override
  public ArrayList<AstNode> getChildren() {
    ArrayList<AstNode> children = new ArrayList<>();
    if (property != null) {
      children.add(property);
    }
    if (expression != null) {
      children.add(expression);
    }
    return children;
  }

  @SuppressWarnings("PatternVariableHidesField")
  @Override
  public AstNode addChild(AstNode child) {
    if (child instanceof Property property) {
      this.property = property;
    } else if (child instanceof Expression expression) {
      this.expression = expression;
    }
    return this;
  }
}
