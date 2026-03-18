package nl.han.ica.icss.ast;

import java.util.List;

public interface AstNode {
  List<AstNode> getChildren();

  AstNode addChild(AstNode child);

  default String getNodeLabel() {
    return getClass().getSimpleName();
  }
}
