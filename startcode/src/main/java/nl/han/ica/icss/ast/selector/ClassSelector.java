package nl.han.ica.icss.ast.selector;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ClassSelector extends Selector {
  public ClassSelector(String identifier) {
    super(identifier.substring( 1));
  }

  @Override
  public String getStringValue() {
    return ".%s".formatted(getIdentifier());
  }
}
