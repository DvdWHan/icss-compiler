package nl.han.ica.icss.ast;

import lombok.*;
import nl.han.ica.icss.checker.SemanticError;

import java.util.ArrayList;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Ast {
  private Stylesheet root = new Stylesheet();

  public ArrayList<SemanticError> getErrors() {
    ArrayList<SemanticError> errors = new ArrayList<>();
    collectErrors(errors, root);
    return errors;
  }

  private void collectErrors(ArrayList<SemanticError> errors, AstNode node) {
    if (node.hasError()) {
      errors.add(node.getError());
    }
    for (AstNode child : node.getChildren()) {
      collectErrors(errors, child);
    }
  }

  @Override
  public String toString() {
    return root.toString();
  }
}
