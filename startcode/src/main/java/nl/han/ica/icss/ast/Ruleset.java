package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
public class Ruleset implements AstNode {
  private final Selector selector;
  private final List<AstNode> declarations;

  public Ruleset(Selector selector) {
    this.selector = selector;
    this.declarations = new ArrayList<>();
  }

  @Override
  public List<AstNode> getChildren() {
    List<AstNode> children = new ArrayList<>();
    children.add(selector);
    children.addAll(declarations);
    return Collections.unmodifiableList(children);
  }

  @Override
  public AstNode addChild(AstNode child) {
    declarations.add(child);
    return this;
  }
}
