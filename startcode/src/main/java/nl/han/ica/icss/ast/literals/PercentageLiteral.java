package nl.han.ica.icss.ast.literals;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.Literal;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@SuppressWarnings("ClassCanBeRecord")
public class PercentageLiteral implements Literal<Integer> {
  private final Integer value;

  public PercentageLiteral(String value) {
    this.value = Integer.parseInt(value.substring(0, value.length() - 1));
  }
}
