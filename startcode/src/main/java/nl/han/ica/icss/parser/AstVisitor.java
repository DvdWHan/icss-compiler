package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.expression.Expression;
import nl.han.ica.icss.ast.expression.math.MathExpression;
import nl.han.ica.icss.ast.expression.math.operation.*;
import nl.han.ica.icss.ast.literal.BooleanLiteral;
import nl.han.ica.icss.ast.literal.ColorLiteral;
import nl.han.ica.icss.ast.literal.numeric.PercentageLiteral;
import nl.han.ica.icss.ast.literal.numeric.PixelLiteral;
import nl.han.ica.icss.ast.literal.numeric.ScalarLiteral;
import nl.han.ica.icss.ast.selector.ClassSelector;
import nl.han.ica.icss.ast.selector.ElementSelector;
import nl.han.ica.icss.ast.selector.IdSelector;
import nl.han.ica.icss.ast.variable.VariableAssignment;
import nl.han.ica.icss.ast.variable.VariableIdentifier;
import org.antlr.v4.runtime.tree.ParseTree;

public class AstVisitor extends IcssBaseVisitor<AstNode> implements AstParser {
  public Ast buildAst(ParseTree parseTree) {
    var stylesheet = (Stylesheet)visit(parseTree);
    return new Ast(stylesheet);
  }

  @Override
  public AstNode visitStylesheet(IcssParser.StylesheetContext context) {
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
  public AstNode visitVariableAssignment(IcssParser.VariableAssignmentContext context) {
    var variableIdentifier = (VariableIdentifier)visit(context.variableIdentifier());
    var expression = (Expression)visit(context.expression());
    return new VariableAssignment().addChild(variableIdentifier).addChild(expression);
  }

  @Override
  public AstNode visitVariableIdentifier(IcssParser.VariableIdentifierContext context) {
    String identifier = context.getText();
    return new VariableIdentifier(identifier);
  }

  @Override
  public AstNode visitExpression(IcssParser.ExpressionContext context) {
    if (context.booleanLiteral() != null) {
      return visit(context.booleanLiteral());
    } else if (context.colorLiteral() != null) {
      return visit(context.colorLiteral());
    } else {
      return visit(context.mathExpression());
    }
  }

  @Override
  public AstNode visitBooleanLiteral(IcssParser.BooleanLiteralContext context) {
    String stringValue = context.getText();
    return new BooleanLiteral(stringValue);
  }

  @Override
  public AstNode visitColorLiteral(IcssParser.ColorLiteralContext context) {
    String stringValue = context.getText();
    return new ColorLiteral(stringValue);
  }

  @Override
  public AstNode visitMathExpression(IcssParser.MathExpressionContext context) {
    return visit(context.additionExpression());
  }

  @Override
  public AstNode visitAdditionExpression(IcssParser.AdditionExpressionContext context) {
    MathExpression left = (MathExpression)visit(context.multiplicationExpression(0));
    for (int i = 1; i < context.multiplicationExpression().size(); ++i) {
      MathExpression right = (MathExpression)visit(context.multiplicationExpression(i));
      String operator = context.getChild(2 * i - 1).getText();
      left = switch (operator) {
        case "+" -> new BinaryAddition(left, right);
        case "-" -> new BinarySubtraction(left, right);
        default -> throw new RuntimeException("Unexpected operator: " + operator);
      };
    }
    return left;
  }

  @Override
  public AstNode visitMultiplicationExpression(IcssParser.MultiplicationExpressionContext context) {
    MathExpression left = (MathExpression)visit(context.unaryExpression(0));
    for (int i = 1; i < context.unaryExpression().size(); ++i) {
      MathExpression right = (MathExpression)visit(context.unaryExpression(i));
      String operator = context.getChild(2 * i - 1).getText();
      if (!operator.equals("*")) {
        throw new RuntimeException("Unexpected operator: " + operator);
      }
      left = new BinaryMultiplication(left, right);
    }
    return left;
  }

  @Override
  public AstNode visitUnaryExpression(IcssParser.UnaryExpressionContext context) {
    if (context.primaryExpression() != null) {
      return visit(context.primaryExpression());
    }
    MathExpression operand = (MathExpression)visit(context.unaryExpression());
    String operator = context.getChild(0).getText();
    return switch (operator) {
      case "+" -> new UnaryPlus(operand);
      case "-" -> new UnaryMinus(operand);
      default -> throw new RuntimeException("Unexpected operator: " + operator);
    };
  }

  @Override
  public AstNode visitPrimaryExpression(IcssParser.PrimaryExpressionContext context) {
    if (context.numericLiteral() != null) {
      return visit(context.numericLiteral());
    } else {
      return visit(context.variableIdentifier());
    }
  }

  @Override
  public AstNode visitNumericLiteral(IcssParser.NumericLiteralContext context) {
    if (context.scalarLiteral() != null) {
      return new ScalarLiteral(context.getText());
    } else if (context.pixelLiteral() != null) {
      return new PixelLiteral(context.getText());
    } else if (context.percentageLiteral() != null) {
      return new PercentageLiteral(context.getText());
    }
    throw new RuntimeException("Invalid numeric literal");
  }

  @Override
  public AstNode visitRuleset(IcssParser.RulesetContext context) {
    var ruleset = new Ruleset();
    ruleset.addChild(visit(context.selector()));
    for (var variableAssignmentContext : context.variableAssignment()) {
      ruleset.addChild(visit(variableAssignmentContext));
    }
    for (var declarationContext : context.declaration()) {
      ruleset.addChild(visit(declarationContext));
    }
    return ruleset;
  }

  @Override
  public AstNode visitSelector(IcssParser.SelectorContext context) {
    if (context.elementSelector() != null) {
      return visit(context.elementSelector());
    } else if (context.idSelector() != null) {
      return visit(context.idSelector());
    } else {
      return visit(context.classSelector());
    }
  }

  @Override
  public AstNode visitElementSelector(IcssParser.ElementSelectorContext context) {
    String identifier = context.getText();
    return new ElementSelector(identifier);
  }

  @Override
  public AstNode visitIdSelector(IcssParser.IdSelectorContext context) {
    String identifier = context.getText();
    return new IdSelector(identifier);
  }

  @Override
  public AstNode visitClassSelector(IcssParser.ClassSelectorContext context) {
    String identifier = context.getText();
    return new ClassSelector(identifier);
  }

  @Override
  public AstNode visitDeclaration(IcssParser.DeclarationContext context) {
    var property = (Property)visit(context.property());
    var expression = (Expression)visit(context.expression());
    return new Declaration().addChild(property).addChild(expression);
  }

  @Override
  public AstNode visitProperty(IcssParser.PropertyContext context) {
    String identifier = context.getText();
    return new Property(identifier);
  }
}
