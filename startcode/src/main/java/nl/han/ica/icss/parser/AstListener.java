package nl.han.ica.icss.parser;


import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.expression.VariableIdentifier;
import nl.han.ica.icss.ast.expression.binary.BinaryAddition;
import nl.han.ica.icss.ast.expression.binary.BinaryMultiplication;
import nl.han.ica.icss.ast.expression.binary.BinarySubtraction;
import nl.han.ica.icss.ast.expression.literal.BooleanLiteral;
import nl.han.ica.icss.ast.expression.literal.ColorLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.PercentageLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.PixelLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.ScalarLiteral;
import nl.han.ica.icss.ast.expression.unary.UnaryMinus;
import nl.han.ica.icss.ast.expression.unary.UnaryPlus;
import nl.han.ica.icss.ast.selector.ClassSelector;
import nl.han.ica.icss.ast.selector.ElementSelector;
import nl.han.ica.icss.ast.selector.IdSelector;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

@NoArgsConstructor
public class AstListener extends IcssBaseListener implements AstParser {
  private final ParseTreeProperty<AstNode> nodes = new ParseTreeProperty<>();

  public Ast buildAst(ParseTree parseTree) {
    ParseTreeWalker walker = new ParseTreeWalker();
    walker.walk(this, parseTree);
    var stylesheet = (Stylesheet)nodes.get(parseTree);
    return new Ast(stylesheet);
  }

  @Override
  public void exitStylesheet(IcssParser.StylesheetContext context) {
    var stylesheet = new Stylesheet();
    for (var variableAssignmentContext : context.variableAssignment()) {
      stylesheet.addChild(nodes.get(variableAssignmentContext));
    }
    for (var rulesetContext : context.ruleset()) {
      stylesheet.addChild(nodes.get(rulesetContext));
    }
    nodes.put(context, stylesheet);
  }

  @Override
  public void exitVariableAssignment(IcssParser.VariableAssignmentContext context) {
    var variableIdentifier = (VariableIdentifier)nodes.get(context.variableIdentifier());
    var expression = (Expression)nodes.get(context.expression());
    var variableAssignment = new VariableAssignment(variableIdentifier, expression);
    nodes.put(context, variableAssignment);
  }

  @Override
  public void exitVariableIdentifier(IcssParser.VariableIdentifierContext context) {
    String identifier = context.getText();
    var variableIdentifier = new VariableIdentifier(identifier);
    nodes.put(context, variableIdentifier);
  }

  @Override
  public void exitExpression(IcssParser.ExpressionContext context) {
    if (context.booleanLiteral() != null) {
      nodes.put(context, nodes.get(context.booleanLiteral()));
    } else if (context.colorLiteral() != null) {
      nodes.put(context, nodes.get(context.colorLiteral()));
    } else if (context.mathExpression() != null) {
      nodes.put(context, nodes.get(context.mathExpression()));
    }
  }

  @Override
  public void exitBooleanLiteral(IcssParser.BooleanLiteralContext context) {
    String valueString = context.getText();
    boolean value = Boolean.parseBoolean(valueString);
    nodes.put(context, new BooleanLiteral(value));
  }

  @Override
  public void exitColorLiteral(IcssParser.ColorLiteralContext context) {
    String valueString = context.getText();
    String value = valueString.substring(1);
    nodes.put(context, new ColorLiteral(value));
  }

  @Override
  public void exitMathExpression(IcssParser.MathExpressionContext context) {
    nodes.put(context, nodes.get(context.additionExpression()));
  }

  @Override
  public void exitAdditionExpression(IcssParser.AdditionExpressionContext context) {
    var left = (Expression)nodes.get(context.multiplicationExpression(0));
    for (int i = 1; i < context.multiplicationExpression().size(); ++i) {
      var right = (Expression)nodes.get(context.multiplicationExpression(i));
      var operator = context.getChild(2 * i - 1).getText();
      left = switch (operator) {
        case "+" -> new BinaryAddition(left, right);
        case "-" -> new BinarySubtraction(left, right);
        default -> throw new RuntimeException("Unexpected operator '%s': expected + or -".formatted(operator));
      };
    }
    nodes.put(context, left);
  }

  @Override
  public void exitMultiplicationExpression(IcssParser.MultiplicationExpressionContext context) {
    var left = (Expression)nodes.get(context.unaryExpression(0));
    for (int i = 1; i < context.unaryExpression().size(); ++i) {
      var right = (Expression)nodes.get(context.unaryExpression(i));
      var operator = context.getChild(2 * i - 1).getText();
      if (!operator.equals("*")) {
        throw new RuntimeException("Unexpected operator '%s': expected *".formatted(operator));
      }
      left = new BinaryMultiplication(left, right);
    }
    nodes.put(context, left);
  }

  @Override
  public void exitUnaryExpression(IcssParser.UnaryExpressionContext context) {
    if (context.primaryExpression() != null) {
      var primaryExpression = context.primaryExpression();
      nodes.put(context, nodes.get(primaryExpression));
      return;
    }
    Expression operand = (Expression)nodes.get(context.unaryExpression());
    String operator = context.getChild(0).getText();
    operand = switch (operator) {
      case "+" -> new UnaryPlus(operand);
      case "-" -> new UnaryMinus(operand);
      default -> throw new RuntimeException("Unexpected operator '%s': expected + or -".formatted(operator));
    };
    nodes.put(context, operand);
  }

  @Override
  public void exitPrimaryExpression(IcssParser.PrimaryExpressionContext context) {
    if (context.numericLiteral() != null) {
      nodes.put(context, nodes.get(context.numericLiteral()));
    }
    nodes.put(context, nodes.get(context.variableIdentifier()));
  }

  @Override
  public void exitScalarLiteral(IcssParser.ScalarLiteralContext context) {
    String valueString = context.getText();
    int value = Integer.parseInt(valueString);
    nodes.put(context, new ScalarLiteral(value));
  }

  @Override
  public void exitPixelLiteral(IcssParser.PixelLiteralContext context) {
    String valueString = context.getText();
    int value = Integer.parseInt(valueString.substring(0, valueString.length() - 2));
    nodes.put(context, new PixelLiteral(value));
  }

  @Override
  public void exitPercentageLiteral(IcssParser.PercentageLiteralContext context) {
    String valueString = context.getText();
    int value = Integer.parseInt(valueString.substring(0, valueString.length() - 1));
    nodes.put(context, new PercentageLiteral(value));
  }

  @Override
  public void exitRuleset(IcssParser.RulesetContext context) {
    var selector = (Selector)nodes.get(context.selector());
    var body = (Body)nodes.get(context.body());
    var ruleset = new Ruleset(selector, body);
    nodes.put(context, ruleset);
  }

  @Override
  public void exitSelector(IcssParser.SelectorContext context) {
    if (context.elementSelector() != null) {
      nodes.put(context, nodes.get(context.elementSelector()));
    } else if (context.idSelector() != null) {
      nodes.put(context, nodes.get(context.idSelector()));
    }
    nodes.put(context, nodes.get(context.classSelector()));
  }

  @Override
  public void exitElementSelector(IcssParser.ElementSelectorContext context) {
    String identifier = context.getText();
    var elementSelector = new ElementSelector(identifier);
    nodes.put(context, elementSelector);
  }

  @Override
  public void exitIdSelector(IcssParser.IdSelectorContext context) {
    String identifier = context.getText();
    var idSelector = new IdSelector(identifier);
    nodes.put(context, idSelector);
  }

  @Override
  public void exitClassSelector(IcssParser.ClassSelectorContext context) {
    String identifier = context.getText();
    var classSelector = new ClassSelector(identifier);
    nodes.put(context, classSelector);
  }

  @Override
  public void exitBody(IcssParser.BodyContext context) {
    var body = new Body();
    for (var variableAssignmentContext : context.variableAssignment()) {
      body.addChild(nodes.get(variableAssignmentContext));
    }
    for (var declarationContext : context.declaration()) {
      body.addChild(nodes.get(declarationContext));
    }
    for (var conditionalStatementContext : context.conditionalStatement()) {
      body.addChild(nodes.get(conditionalStatementContext));
    }
    nodes.put(context, body);
  }

  @Override
  public void exitDeclaration(IcssParser.DeclarationContext context) {
    var property = (Property)nodes.get(context.property());
    var expression = (Expression)nodes.get(context.expression());
    var declaration = new Declaration(property, expression);
    nodes.put(context, declaration);
  }

  @Override
  public void exitProperty(IcssParser.PropertyContext context) {
    String propertyName = context.getText();
    var property = new Property(propertyName);
    nodes.put(context, property);
  }
}
