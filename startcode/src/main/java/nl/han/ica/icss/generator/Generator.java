package nl.han.ica.icss.generator;


import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.expression.Literal;

@NoArgsConstructor
public class Generator {
  public String generate(Ast ast) {
    return visitStylesheet(ast.getRoot()).toString();
  }

  private StringBuilder visitStylesheet(Stylesheet stylesheet) {
    var sb = new StringBuilder();
    for (Ruleset ruleset : stylesheet.getRulesets()) {
      visitRuleset(ruleset, sb).append("\n");
    }
    return sb;
  }

  private StringBuilder visitRuleset(Ruleset ruleset, StringBuilder sb) {
    visitSelector(ruleset.getSelector(), sb).append(" {\n");
    visitBody(ruleset.getBody(), sb).append("}");
    return sb;
  }

  private StringBuilder visitSelector(Selector selector, StringBuilder sb) {
    sb.append(selector.getIdentifierString());
    return sb;
  }

  private StringBuilder visitBody(Body body, StringBuilder sb) {
    for (Declaration declaration : body.getDeclarations()) {
      visitDeclaration(declaration, sb).append("\n");
    }
    return sb;
  }

  private StringBuilder visitDeclaration(Declaration declaration, StringBuilder sb) {
    visitProperty(declaration.getProperty(), sb).append(": ");
    visitLiteral((Literal<?>)declaration.getExpression(), sb).append(";");
    return sb;
  }

  private StringBuilder visitProperty(Property property, StringBuilder sb) {
    sb.append(property.getIdentifier());
    return sb;
  }

  private StringBuilder visitLiteral(Literal<?> literal, StringBuilder sb) {
    sb.append(literal.getValueString());
    return sb;
  }
}
