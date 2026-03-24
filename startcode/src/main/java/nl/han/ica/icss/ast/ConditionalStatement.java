package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
public class ConditionalStatement extends AstNode {
  @Getter private final Expression condition;
  @Getter private final Body ifBody;
  @Getter private final Body elseBody;

  public ConditionalStatement(Expression condition, Body ifBody) {
    this(condition, ifBody, new Body());
  }

  public ConditionalStatement(Expression condition, Body ifBody, Body elseBody) {
    this.condition = condition;
    this.ifBody = ifBody;
    this.elseBody = elseBody;
    addChild(condition);
    addChild(ifBody);
    addChild(elseBody);
  }
}
