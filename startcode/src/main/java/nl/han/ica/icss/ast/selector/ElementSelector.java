package nl.han.ica.icss.ast.selector;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ElementSelector extends Selector {
  public ElementSelector(String identifier) {
    super(identifier);
  }
}
