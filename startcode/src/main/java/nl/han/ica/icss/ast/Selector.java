package nl.han.ica.icss.ast;

import java.util.List;

public interface Selector extends AstNode {
  String getIdentifier();

  @Override
  default List<AstNode> getChildren() {
    return List.of();
  }

  @Override
  default AstNode addChild(AstNode child) {
    return this;
  }

  @Override
  default String getNodeLabel() {
    return "%s(%s)".formatted(getClass().getSimpleName(), getIdentifier());
  }
}
