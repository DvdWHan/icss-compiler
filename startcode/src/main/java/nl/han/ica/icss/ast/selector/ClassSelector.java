package nl.han.ica.icss.ast.selector;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.Selector;

@EqualsAndHashCode(callSuper = true)
public class ClassSelector extends Selector {
  public ClassSelector(String identifier) {
    super(identifier);
  }

  @Override
  public String getIdentifierString() {
    return ".%s".formatted(getIdentifier());
  }
}
