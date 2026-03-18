package nl.han.ica.icss.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Stylesheet extends AstNode {
  private final List<AstNode> rulesets = new ArrayList<>();

  @Override
  public String getNodeLabel() {
    return "Stylesheet";
  }

  @Override
  public List<AstNode> getChildren() {
    return this.rulesets;
  }

  @Override
  public AstNode addChild(AstNode child) {
    rulesets.add(child);
    return this;
  }

  @Override
  public AstNode removeChild(AstNode child) {
    rulesets.remove(child);
    return this;
  }
}
