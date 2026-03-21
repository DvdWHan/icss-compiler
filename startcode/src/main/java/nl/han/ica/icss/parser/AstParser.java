package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.Ast;
import org.antlr.v4.runtime.tree.ParseTree;

public interface AstParser {
  Strategy STRATEGY = Strategy.VISITOR;

  Ast buildAst(ParseTree parseTree);

  static AstParser build() {
    return switch (STRATEGY) {
      case LISTENER -> new AstListener();
      case VISITOR -> new AstVisitor();
    };
  }

  enum Strategy {
    LISTENER,
    VISITOR,
  }
}
