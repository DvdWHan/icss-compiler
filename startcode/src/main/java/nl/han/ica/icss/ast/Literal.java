package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.literals.*;

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

  static Literal<?> of(String value) {
    if (value == null) {
      return null;
    }
    if (value.matches("^(TRUE|FALSE)$")) {
      return new BooleanLiteral(value);
    }
    if (value.matches("^(\\d+)$")) {
      return new ScalarLiteral(value);
    }
    if (value.matches("^(\\d+%)$")) {
      return new PercentageLiteral(value);
    }
    if (value.matches("^(\\d+px)$")) {
      return new PixelLiteral(value);
    }
    if (value.matches("^(#([0-9a-f]){6})$")) {
      return new ColorLiteral(value);
    }
    throw new IllegalArgumentException("Illegal literal: %s".formatted(value));
  }
}
