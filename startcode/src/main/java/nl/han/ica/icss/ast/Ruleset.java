package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
public class Ruleset extends AstNode<Ruleset> {
  @Getter private final Selector selector;
  @Getter private final Body body;

  public Ruleset(Selector selector, Body body) {
    this.selector = selector;
    this.body = body;
    addChild(selector);
    addChild(body);
  }
}
