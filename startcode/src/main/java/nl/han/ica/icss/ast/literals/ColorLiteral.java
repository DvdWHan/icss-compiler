package nl.han.ica.icss.ast.literals;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.Literal;

@Getter
@EqualsAndHashCode
@SuppressWarnings("ClassCanBeRecord")
public class ColorLiteral implements Literal<String> {
  private final String value;

  public ColorLiteral(String value) {
    this.value = value.substring(1);
  }
}
