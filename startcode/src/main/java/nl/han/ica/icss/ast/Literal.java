package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.unused.Expression;

import java.util.List;

public interface Literal<T> extends Expression {
  T getValue();

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
    return "%s(%s)".formatted(getClass().getSimpleName(), getValue());
  }
}
