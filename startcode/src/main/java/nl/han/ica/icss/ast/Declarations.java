package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
public class Declarations implements AstNode {
  private final List<AstNode> declarations = new ArrayList<>();

  @Override
  public List<AstNode> getChildren() {
    return Collections.unmodifiableList(declarations);
  }

  @Override
  public AstNode addChild(AstNode child) {
    if (child instanceof Declaration declaration) {
      this.declarations.add(declaration);
    }
    return this;
  }
}
