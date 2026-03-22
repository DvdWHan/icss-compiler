package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public abstract class Expression extends AstNode {
  public abstract Type getType();

  public enum Type {
    BOOLEAN,
    COLOR,
    PERCENTAGE,
    PIXEL,
    SCALAR,
    UNDEFINED;
  }
}
