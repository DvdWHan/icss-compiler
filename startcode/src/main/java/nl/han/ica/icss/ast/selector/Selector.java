package nl.han.ica.icss.ast.selector;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.AstNode;

@EqualsAndHashCode(callSuper = true)
public abstract class Selector extends AstNode {
  @Getter private final String identifier;

  public Selector(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public String getNodeLabel() {
    return "%s(%s)".formatted(getClass().getSimpleName(), getIdentifier());
  }
}
