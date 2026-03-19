package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
public class Stylesheet implements AstNode {
  private final List<AstNode> rulesets = new ArrayList<>();

  @Override
  public List<AstNode> getChildren() {
    return Collections.unmodifiableList(this.rulesets);
  }

  @Override
  public AstNode addChild(AstNode child) {
    rulesets.add(child);
    return this;
  }

  @Override
  public String toString() {
    return AstNode.toString(this);
  }
}
