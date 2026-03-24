package nl.han.ica.icss.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Property extends AstNode<Property> {
  @Getter private final String identifier;

  @Override
  public String getNodeLabel() {
    return "%s(%s)".formatted(getClass().getSimpleName(), identifier);
  }
}
