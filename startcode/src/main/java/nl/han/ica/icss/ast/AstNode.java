package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nl.han.ica.icss.checker.SemanticError;

import java.util.List;

@EqualsAndHashCode
public abstract class AstNode {
  @Getter @Setter private AstNode parent;
  @Getter private SemanticError error = null;

  public List<AstNode> getChildren() {
    return List.of();
  }

  public AstNode addChild(AstNode child) {
    return this;
  }

  public void removeChild(AstNode child) {
    throw new UnsupportedOperationException();
  }

  public String getNodeLabel() {
    return getClass().getSimpleName();
  }

  public boolean hasError() {
    return error != null;
  }

  public void setError(String message) {
    this.error = new SemanticError(message);
  }

  public String toString() {
    var sb = new StringBuilder();
    toString(this, sb, 0);
    return sb.toString();
  }

  private static void toString(AstNode node, StringBuilder sb, int indentation) {
    if (node == null) {
      return;
    }
    sb.append("\t".repeat(indentation));
    sb.append(node.getNodeLabel());
    if (node.getChildren().isEmpty()) {
      return;
    }
    sb.append("[\n");
    List<AstNode> children = node.getChildren();
    for (int i = 0; i < children.size(); ++i) {
      AstNode child = children.get(i);
      toString(child, sb, indentation + 1);
      if (i < children.size() - 1) {
        sb.append(",");
      }
      sb.append("\n");
    }
    sb.append("\t".repeat(indentation)).append("]");
  }
}
