package nl.han.ica.icss.ast.selectors;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.Selector;

@EqualsAndHashCode
@AllArgsConstructor
@SuppressWarnings("ClassCanBeRecord")
public class IdSelector implements Selector {
  private final String identifier;

  @Override
  public String getIdentifier() {
    return this.identifier;
  }
}
