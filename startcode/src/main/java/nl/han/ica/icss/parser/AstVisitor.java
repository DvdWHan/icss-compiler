package nl.han.ica.icss.parser;

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

import java.util.Optional;

public class AstVisitor extends IcssBaseVisitor<AstNode<?>> implements AstParser {
  public Ast buildAst(ParseTree parseTree) {
    var stylesheet = (Stylesheet)visit(parseTree);
    return new Ast(stylesheet);
  }

  @Override
  public AstNode<?> visitStylesheet(IcssParser.StylesheetContext context) {
    var stylesheet = new Stylesheet();
    for (var variableAssignmentContext : context.variableAssignment()) {
      stylesheet.addChild(visit(variableAssignmentContext));
    }
    for (var rulesetContext : context.ruleset()) {
      stylesheet.addChild(visit(rulesetContext));
    }
    return stylesheet;
  }

  @Override
  public AstNode<?> visitVariableAssignment(IcssParser.VariableAssignmentContext context) {
    var identifier = (VariableIdentifier)visit(context.variableIdentifier());
    var expression = (Expression)visit(context.expression());
    return new VariableAssignment(identifier, expression);
  }

  @Override
  public AstNode<?> visitVariableIdentifier(IcssParser.VariableIdentifierContext context) {
    String identifier = context.getText();
    return new VariableIdentifier(identifier);
  }

  @Override
  public AstNode<?> visitExpression(IcssParser.ExpressionContext context) {
    if (context.booleanLiteral() != null) {
      return visit(context.booleanLiteral());
    } else if (context.colorLiteral() != null) {
      return visit(context.colorLiteral());
    }
    return visit(context.mathExpression());
  }

  @Override
  public AstNode<?> visitBooleanLiteral(IcssParser.BooleanLiteralContext context) {
    String valueString = context.getText();
    boolean value = Boolean.parseBoolean(valueString);
    return new BooleanLiteral(value);
  }

  @Override
  public AstNode<?> visitColorLiteral(IcssParser.ColorLiteralContext context) {
    String valueString = context.getText();
    String value = valueString.substring(1);
    return new ColorLiteral(value);
  }

  @Override
  public AstNode<?> visitMathExpression(IcssParser.MathExpressionContext context) {
    return visit(context.additionExpression());
  }

  @Override
  public AstNode<?> visitAdditionExpression(IcssParser.AdditionExpressionContext context) {
    var left = (Expression)visit(context.multiplicationExpression(0));
    for (int i = 1; i < context.multiplicationExpression().size(); ++i) {
      var right = (Expression)visit(context.multiplicationExpression(i));
      String operator = context.getChild(2 * i - 1).getText();
      left = switch (operator) {
        case "+" -> new BinaryAddition(left, right);
        case "-" -> new BinarySubtraction(left, right);
        default -> throw new RuntimeException("Unexpected operator '%s': expected + or -".formatted(operator));
      };
    }
    return left;
  }

  @Override
  public AstNode<?> visitMultiplicationExpression(IcssParser.MultiplicationExpressionContext context) {
    var left = (Expression)visit(context.unaryExpression(0));
    for (int i = 1; i < context.unaryExpression().size(); ++i) {
      var right = (Expression)visit(context.unaryExpression(i));
      String operator = context.getChild(2 * i - 1).getText();
      if (!operator.equals("*")) {
        throw new RuntimeException("Unexpected operator '%s': expected *".formatted(operator));
      }
      left = new BinaryMultiplication(left, right);
    }
    return left;
  }

  @Override
  public AstNode<?> visitUnaryExpression(IcssParser.UnaryExpressionContext context) {
    if (context.primaryExpression() != null) {
      return visit(context.primaryExpression());
    }
    var operand = (Expression)visit(context.unaryExpression());
    String operator = context.getChild(0).getText();
    return switch (operator) {
      case "+" -> new UnaryPlus(operand);
      case "-" -> new UnaryMinus(operand);
      default -> throw new RuntimeException("Unexpected operator '%s': expected + or -".formatted(operator));
    };
  }

  @Override
  public AstNode<?> visitPrimaryExpression(IcssParser.PrimaryExpressionContext context) {
    if (context.numericLiteral() != null) {
      return visit(context.numericLiteral());
    }
    return visit(context.variableIdentifier());

  }

  @Override
  public AstNode<?> visitScalarLiteral(IcssParser.ScalarLiteralContext context) {
    String valueString = context.getText();
    int value = Integer.parseInt(valueString);
    return new ScalarLiteral(value);
  }

  @Override
  public AstNode<?> visitPixelLiteral(IcssParser.PixelLiteralContext context) {
    String valueString = context.getText();
    int value = Integer.parseInt(valueString.substring(0, valueString.length() - 2));
    return new PixelLiteral(value);
  }

  @Override
  public AstNode<?> visitPercentageLiteral(IcssParser.PercentageLiteralContext context) {
    String valueString = context.getText();
    int value = Integer.parseInt(valueString.substring(0, valueString.length() - 1));
    return new PercentageLiteral(value);
  }

  @Override
  public AstNode<?> visitRuleset(IcssParser.RulesetContext context) {
    var selector = (Selector)visit(context.selector());
    var body = (Body)visit(context.body());
    return new Ruleset(selector, body);
  }

  @Override
  public AstNode<?> visitSelector(IcssParser.SelectorContext context) {
    if (context.elementSelector() != null) {
      return visit(context.elementSelector());
    } else if (context.idSelector() != null) {
      return visit(context.idSelector());
    }
    return visit(context.classSelector());
  }

  @Override
  public AstNode<?> visitElementSelector(IcssParser.ElementSelectorContext context) {
    String identifier = context.getText();
    return new ElementSelector(identifier);
  }

  @Override
  public AstNode<?> visitIdSelector(IcssParser.IdSelectorContext context) {
    String identifierString = context.getText();
    String identifier = identifierString.substring(1);
    return new IdSelector(identifier);
  }

  @Override
  public AstNode<?> visitClassSelector(IcssParser.ClassSelectorContext context) {
    String identifierString = context.getText();
    String identifier = identifierString.substring(1);
    return new ClassSelector(identifier);
  }

  @Override
  public AstNode<?> visitBody(IcssParser.BodyContext context) {
    var body = new Body();
    for (ParseTree child : context.children) {
      if (child instanceof IcssParser.VariableAssignmentContext variableAssignmentContext) {
        body.addChild(visit(variableAssignmentContext));
      } else if (child instanceof IcssParser.DeclarationContext declarationContext) {
        body.addChild(visit(declarationContext));
      } else if (child instanceof IcssParser.ConditionalStatementContext conditionalStatementContext) {
        body.addChild(visit(conditionalStatementContext));
      }
    }
    return body;
  }

  @Override
  public AstNode<?> visitDeclaration(IcssParser.DeclarationContext context) {
    var property = (Property)visit(context.property());
    var expression = (Expression)visit(context.expression());
    return new Declaration(property, expression);
  }

  @Override
  public AstNode<?> visitProperty(IcssParser.PropertyContext context) {
    String identifier = context.getText();
    return new Property(identifier);
  }

  @Override
  public AstNode<?> visitConditionalStatement(IcssParser.ConditionalStatementContext context) {
    var condition = (Expression)visit(context.expression());
    var ifBody = (Body)visit(context.body(0));
    Optional<Body> elseBody = Optional.ofNullable(context.body().size() > 1 ? (Body)visit(context.body(1)) : null);
    return new ConditionalStatement(condition, ifBody, elseBody.orElseGet(Body::new));
  }
}
