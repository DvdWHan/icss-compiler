package nl.han.ica.icss.ast;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.han.ica.datastructures.IHanStack;
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
    if (node == null) {
      return "";
    }
    var sb = new StringBuilder("[");
    sb.append(node.getNodeLabel());
    for (AstNode child : node.getChildren()) {
      sb.append(toString(child)).append(", ");
    }
    if (!node.getChildren().isEmpty()) {
      sb.delete(sb.length() - 2, sb.length());
    }
    return sb.append("]").toString();
  }
}
