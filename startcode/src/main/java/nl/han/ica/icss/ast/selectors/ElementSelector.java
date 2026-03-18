package nl.han.ica.icss.ast.selectors;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.Selector;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ElementSelector extends Selector {
  private final String element;

  public String getNodeLabel() {
    return "ElementSelector(%s)".formatted(element);
  }

  @Override
  public String toString() {
    return this.element;
  }
}
