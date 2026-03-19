package nl.han.ica.icss.ast;

public interface Expression extends AstNode {
  Type getType();

  enum Type {
    BOOLEAN,
    COLOR,
    PERCENTAGE,
    PIXEL,
    SCALAR,
    UNDEFINED,
    VARIABLE,
  }
}
