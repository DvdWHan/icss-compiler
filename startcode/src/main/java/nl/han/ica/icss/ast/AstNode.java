package nl.han.ica.icss.ast;

import lombok.Getter;
import nl.han.ica.icss.checker.SemanticError;

import java.util.ArrayList;
import java.util.Objects;

@Getter
public class AstNode {
  private SemanticError error = null;

  public String getNodeLabel() {
    return "AstNode";
  }

  public ArrayList<AstNode> getChildren() {
    return new ArrayList<>();
  }

  public AstNode addChild(AstNode child) {
    return this;
  }

  public AstNode removeChild(AstNode child) {
    return this;
  }

  public void setError(String description) {
    this.error = new SemanticError(description);
  }

  public boolean hasError() {
    return error != null;
  }

  @Override
  public String toString() {
    var sb = new StringBuilder("[");
    sb.append(getNodeLabel()).append("|");
    for (AstNode child : getChildren()) {
      sb.append(child.toString());
    }
    return sb.append("]").toString();
  }

  @Override
  public boolean equals(Object object) {
    return object instanceof AstNode astNode && Objects.equals(this.getChildren(), astNode.getChildren());
  }
}
