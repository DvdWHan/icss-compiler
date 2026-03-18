package nl.han.ica.icss.ast;

import lombok.*;
import nl.han.ica.icss.checker.SemanticError;

import java.util.ArrayList;

@Setter
@EqualsAndHashCode
@ToString(includeFieldNames = false)
@NoArgsConstructor
@AllArgsConstructor
public class AST {
  public Stylesheet root = new Stylesheet();

  public ArrayList<SemanticError> getErrors() {
    ArrayList<SemanticError> errors = new ArrayList<>();
    collectErrors(errors, root);
    return errors;
  }

  private void collectErrors(ArrayList<SemanticError> errors, ASTNode node) {
    if (node.hasError()) {
      errors.add(node.getError());
    }
    for (ASTNode child : node.getChildren()) {
      collectErrors(errors, child);
    }
  }
}
