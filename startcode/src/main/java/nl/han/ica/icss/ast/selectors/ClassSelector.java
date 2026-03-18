package nl.han.ica.icss.ast.selectors;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.Selector;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ClassSelector extends Selector {
  private final String clazz;

  @Override
  public String getNodeLabel() {
    return "ClassSelector(%s)".formatted(clazz);
  }

  @Override
  public String toString() {
    return this.clazz;
  }
}
