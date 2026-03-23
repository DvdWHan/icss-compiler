package nl.han.ica.icss.generator;


import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.expression.Literal;

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
      case Literal<?> literal -> visitLiteral(literal, sb);
      default -> throw new IllegalStateException("Unexpected node '%s'".formatted(node.getNodeLabel()));
    };
  }

  private StringBuilder visitStylesheet(Stylesheet stylesheet, StringBuilder sb, int indentation) {
    indent(sb, indentation);
    for (Ruleset ruleset : stylesheet.getRulesets()) {
      visit(ruleset, sb, indentation);
    }
    return sb;
  }

  private StringBuilder visitRuleset(Ruleset ruleset, StringBuilder sb, int indentation) {
    indent(sb, indentation);
    Selector selector = ruleset.getSelector();
    visit(selector, sb, indentation).append(" {\n");
    for (Declaration declaration : ruleset.getDeclarations()) {
      visit(declaration, sb, indentation + 1).append("\n");
    }
    return sb.append("}");
  }

  private StringBuilder visitSelector(Selector selector, StringBuilder sb, int indentation) {
    indent(sb, indentation);
    String identifierString = selector.getIdentifierString();
    return sb.append(identifierString);
  }

  private StringBuilder visitDeclaration(Declaration declaration, StringBuilder sb, int indentation) {
    indent(sb, indentation);
    Property property = declaration.getProperty();
    visit(property, sb, indentation).append(": ");
    Literal<?> literal = (Literal<?>)declaration.getExpression();
    visit(literal, sb, indentation).append(";");
    return sb;
  }

  private StringBuilder visitProperty(Property property, StringBuilder sb) {
    String identifier = property.getIdentifier();
    return sb.append(identifier);
  }

  private StringBuilder visitLiteral(Literal<?> literal, StringBuilder sb) {
    String literalString = literal.getValueString();
    return sb.append(literalString);
  }

  private void indent(StringBuilder sb, int indentation) {
    sb.append("  ".repeat(indentation));
  }
}
