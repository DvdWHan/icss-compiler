package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
public abstract class Selector extends AstNode {
  @Getter private final String identifier;

  public Selector(String identifier) {
    this.identifier = identifier;
  }

  public abstract String getIdentifierString();

  @Override
  public String getNodeLabel() {
    return "%s(%s)".formatted(getClass().getSimpleName(), getIdentifierString());
  }
}
