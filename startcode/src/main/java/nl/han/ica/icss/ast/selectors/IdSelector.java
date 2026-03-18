package nl.han.ica.icss.ast.selectors;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.Selector;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class IdSelector extends Selector {
  private final String id;

  public String getNodeLabel() {
    return "IdSelector(%s)".formatted(id);
  }

  @Override
  public String toString() {
    return this.id;
  }
}
