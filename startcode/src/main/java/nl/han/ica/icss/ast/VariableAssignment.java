package nl.han.ica.icss.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class VariableAssignment implements AstNode {
  private VariableIdentifier identifier = null;
  private Expression expression = null;

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
  public List<AstNode> getChildren() {
    return List.of(identifier, expression);
  }

  @Override
  public String getNodeLabel() {
    return "%s(%s=%s)".formatted(getClass().getSimpleName(), identifier.getNodeLabel(), expression.getNodeLabel());
  }
}
