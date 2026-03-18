package nl.han.ica.icss.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
@SuppressWarnings("ClassCanBeRecord")
public class Declaration implements AstNode {
  private final Property property;
  private final Expression expression;

  @Override
  public List<AstNode> getChildren() {
    return List.of(property, expression);
  }

  @Override
  public AstNode addChild(AstNode child) {
    return this;
  }
}
