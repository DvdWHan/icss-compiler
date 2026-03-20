package nl.han.ica.icss.ast.selector;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class IdSelector extends Selector {
  public IdSelector(String identifier) {
    super(identifier.substring(1));
  }

  @Override
  public String getStringValue() {
    return "#%s".formatted(getIdentifier());
  }
}
