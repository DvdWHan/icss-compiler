package nl.han.ica.icss.generator;


import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.expression.Expression;
import nl.han.ica.icss.ast.selector.Selector;

@NoArgsConstructor
public class Generator {
  public String generate(Ast ast) {
    return visit(ast.getRoot(), new StringBuilder(), 0).toString();
  }

  private StringBuilder visit(AstNode node, StringBuilder sb, int indentation) {
    return switch (node) {
      case Stylesheet stylesheet -> visitStylesheet(stylesheet, sb, indentation);
      case Ruleset ruleset -> visitRuleset(ruleset, sb, indentation);
      case Selector selector -> visitSelector(selector, sb, indentation);
      case Declaration declaration -> visitDeclaration(declaration, sb, indentation);
      case Property property -> visitProperty(property, sb);
      case Expression expression -> visitExpression(expression, sb);
      default -> throw new IllegalStateException("Unexpected node '%s'".formatted(node.getNodeLabel()));
    };
  }

  private StringBuilder visitStylesheet(Stylesheet stylesheet, StringBuilder sb, int indentation) {
    indent(sb, indentation);
    for (AstNode child : stylesheet.getChildren()) {
      if (child instanceof Ruleset ruleset) {
        visit(ruleset, sb, indentation);
      }
    }
    return sb;
  }

  private StringBuilder visitRuleset(Ruleset ruleset, StringBuilder sb, int indentation) {
    indent(sb, indentation);
    for (AstNode child : ruleset.getChildren()) {
      if (child instanceof Selector selector) {
        visit(selector, sb, indentation).append(" {\n");
      }
      if (child instanceof Declaration declaration) {
        visit(declaration, sb, indentation + 1);
      }
    }
    return sb.append("}\n");
  }

  private StringBuilder visitSelector(Selector selector, StringBuilder sb, int indentation) {
    return indent(sb, indentation).append(selector.getIdentifier());
  }

  private StringBuilder visitDeclaration(Declaration declaration, StringBuilder sb, int indentation) {
    indent(sb, indentation);
    for (AstNode child : declaration.getChildren()) {
      if (child instanceof Property property) {
        visit(property, sb, indentation).append(": ");
      } else if (child instanceof Expression expression) {
        visit(expression, sb, indentation).append(";\n");
      }
    }
    return sb;
  }

  private StringBuilder visitProperty(Property property, StringBuilder sb) {
    return sb.append(property.getIdentifier());
  }

  private StringBuilder visitExpression(Expression expression, StringBuilder sb) {
    return sb.append(expression.getStringValue());
  }

  private StringBuilder indent(StringBuilder sb, int indentation) {
    return sb.append("  ".repeat(indentation));
  }
}
