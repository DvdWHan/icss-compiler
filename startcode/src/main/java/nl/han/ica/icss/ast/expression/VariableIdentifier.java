package nl.han.ica.icss.ast.expression;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.han.ica.icss.ast.Expression;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VariableIdentifier extends Expression {
  @Getter private final String identifier;

  @Override
  public Type getType() {
    return Type.UNDEFINED;
  }

  @Override
  public String getNodeLabel() {
    return "%s(%s)".formatted(getClass().getSimpleName(), identifier);
  }
}
