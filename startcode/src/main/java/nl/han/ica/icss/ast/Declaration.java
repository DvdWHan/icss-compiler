package nl.han.ica.icss.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Declaration implements AstNode {
  private Property property = null;
  private Literal<?> literal = null;

  @Override
  public List<AstNode> getChildren() {
    return List.of(property, literal);
  }

  @Override
  @SuppressWarnings("PatternVariableHidesField")
  public AstNode addChild(AstNode child) {
    if (child instanceof Property property) {
      this.property = property;
    } else if (child instanceof Literal<?> literal) {
      this.literal = literal;
    }
    return this;
  }
}
