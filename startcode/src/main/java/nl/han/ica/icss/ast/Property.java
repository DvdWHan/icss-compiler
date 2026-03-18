package nl.han.ica.icss.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
@SuppressWarnings("ClassCanBeRecord")
public class Property implements AstNode {
  private final String name;

  @Override
  public List<AstNode> getChildren() {
    return List.of();
  }

  @Override
  public AstNode addChild(AstNode child) {
    return this;
  }

  @Override
  public String getNodeLabel() {
    return "%s(%s)".formatted(getClass().getSimpleName(), name);
  }
}
