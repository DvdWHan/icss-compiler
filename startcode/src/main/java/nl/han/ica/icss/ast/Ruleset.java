package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class Ruleset extends AstNode {
  private final List<AstNode> selectors;
  private final List<AstNode> declarations;

  public Ruleset() {
    this.selectors = new ArrayList<>();
    this.declarations = new ArrayList<>();
  }

  public Ruleset(Selector selector, ArrayList<AstNode> declarations) {
    this.selectors = new ArrayList<>();
    this.selectors.add(selector);
    this.declarations = declarations;
  }

  @Override
  public String getNodeLabel() {
    return "Ruleset";
  }

  @Override
  public ArrayList<AstNode> getChildren() {
    ArrayList<AstNode> children = new ArrayList<>();
    children.addAll(selectors);
    children.addAll(declarations);
    return children;
  }

  @Override
  public AstNode addChild(AstNode child) {
    if (child instanceof Selector selector) {
      selectors.add(selector);
    } else if (child instanceof Declaration declaration) {
      declarations.add(declaration);
    }
    return this;
  }
}
