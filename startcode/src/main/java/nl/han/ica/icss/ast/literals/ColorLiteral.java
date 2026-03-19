package nl.han.ica.icss.ast.literals;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.Literal;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@SuppressWarnings("ClassCanBeRecord")
public class ColorLiteral implements Literal<String> {
  private final String value;

  @Override
  public Type getType() {
    return Type.COLOR;
  }
}
