package nl.han.ica.icss.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Ruleset implements AstNode {
  private Selector selector = null;
  private Declarations declarations = null;

  @Override
  public List<AstNode> getChildren() {
    return List.of(selector, declarations);
  }

  @Override
  @SuppressWarnings("PatternVariableHidesField")
  public AstNode addChild(AstNode child) {
    if (child instanceof Selector selector) {
      this.selector = selector;
    } else if (child instanceof Declarations declarations) {
      this.declarations = declarations;
    }
    return this;
  }
}
