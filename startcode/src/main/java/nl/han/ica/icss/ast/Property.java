package nl.han.ica.icss.ast;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Property extends AstNode {
  private String name = "undefined";

  @Override
  public String getNodeLabel() {
    return "Property(%s)".formatted(name);
  }
}
