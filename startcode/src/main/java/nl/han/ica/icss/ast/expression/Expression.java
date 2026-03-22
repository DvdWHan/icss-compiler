package nl.han.ica.icss.ast.expression;

import lombok.EqualsAndHashCode;
import nl.han.ica.icss.ast.AstNode;

@EqualsAndHashCode(callSuper = true)
public abstract class Expression extends AstNode {
  public String getStringValue() {
    return "";
  }

  public enum Type {
    BOOLEAN,
    COLOR,
    PERCENTAGE,
    PIXEL,
    SCALAR,
    UNDEFINED;

    @Override
    public String toString() {
      return this.name();
    }
  }
}
