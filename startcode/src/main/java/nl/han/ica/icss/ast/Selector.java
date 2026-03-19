package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.ElementSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;

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

  static Selector of(String identifier) {
    if (identifier.startsWith(".")) {
      return new ClassSelector(identifier);
    }
    if (identifier.startsWith("#")) {
      return new IdSelector(identifier);
    }
    return new ElementSelector(identifier);
  }
}
