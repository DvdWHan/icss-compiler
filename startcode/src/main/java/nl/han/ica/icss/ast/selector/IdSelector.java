package nl.han.ica.icss.ast.selector;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.Selector;

@EqualsAndHashCode(callSuper = true)
public class IdSelector extends Selector {
  public IdSelector(String identifier) {
    super(identifier);
  }

  @Override
  public String getIdentifierString() {
    return "#%s".formatted(getIdentifier());
  }
}
