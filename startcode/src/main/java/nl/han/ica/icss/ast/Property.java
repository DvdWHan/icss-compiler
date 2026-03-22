package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
public class Property extends AstNode {
  @Getter private final String identifier;

  public Property(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public String getNodeLabel() {
    return "%s(%s)".formatted(getClass().getSimpleName(), identifier);
  }
}
