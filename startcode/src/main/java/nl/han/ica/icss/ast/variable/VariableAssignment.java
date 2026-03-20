package nl.han.ica.icss.ast.variable;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.AstNode;
import nl.han.ica.icss.ast.expression.Expression;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class VariableAssignment extends AstNode {
  private VariableIdentifier identifier = null;
  private Expression expression = null;

  public VariableAssignment(VariableIdentifier identifier, Expression expression) {
    this.identifier = identifier;
    this.expression = expression;
  }

  @Override
  public List<AstNode> getChildren() {
    return List.of(identifier, expression);
  }

  @Override
  @SuppressWarnings("PatternVariableHidesField")
  public AstNode addChild(AstNode child) {
    if (child instanceof VariableIdentifier identifier) {
      this.identifier = identifier;
    } else if (child instanceof Expression expression) {
      this.expression = expression;
    }
    return this;
  }

  @Override
  public String getNodeLabel() {
    return "%s(%s=%s)".formatted(getClass().getSimpleName(), identifier.getNodeLabel(), expression.getNodeLabel());
  }
}
