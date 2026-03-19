package nl.han.ica.icss.ast;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.han.ica.icss.checker.SemanticError;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public interface AstNode {
  Map<AstNode, ErrorHandler> ERROR_HANDLERS = new WeakHashMap<>();

  List<AstNode> getChildren();

  AstNode addChild(AstNode child);

  default String getNodeLabel() {
    return getClass().getSimpleName();
  }

  default ErrorHandler getErrorHandler() {
    return ERROR_HANDLERS.computeIfAbsent(this, k -> new ErrorHandler());
  }

  default SemanticError getError() {
    return getErrorHandler().getError();
  }

  default void setError(SemanticError error) {
    getErrorHandler().setError(error);
  }

  default boolean hasError() {
    return getErrorHandler().hasError();
  }

  @Getter
  @Setter
  @NoArgsConstructor
  final class ErrorHandler {
    private SemanticError error = null;

    public boolean hasError() {
      return error != null;
    }
  }

  static String toString(AstNode node) {
    var sb = new StringBuilder();
    toString(node, sb, 0);
    return sb.toString();
  }

  private static void toString(AstNode node, StringBuilder sb, int indentation) {
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
