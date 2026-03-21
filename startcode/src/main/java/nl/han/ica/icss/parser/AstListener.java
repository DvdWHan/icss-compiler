package nl.han.ica.icss.parser;


import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.expression.Expression;
import nl.han.ica.icss.ast.expression.math.MathExpression;
import nl.han.ica.icss.ast.expression.math.operation.*;
import nl.han.ica.icss.ast.literal.BooleanLiteral;
import nl.han.ica.icss.ast.literal.ColorLiteral;
import nl.han.ica.icss.ast.literal.numeric.NumericLiteral;
import nl.han.ica.icss.ast.literal.numeric.PercentageLiteral;
import nl.han.ica.icss.ast.literal.numeric.PixelLiteral;
import nl.han.ica.icss.ast.literal.numeric.ScalarLiteral;
import nl.han.ica.icss.ast.selector.ClassSelector;
import nl.han.ica.icss.ast.selector.ElementSelector;
import nl.han.ica.icss.ast.selector.IdSelector;
import nl.han.ica.icss.ast.variable.VariableAssignment;
import nl.han.ica.icss.ast.variable.VariableIdentifier;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

@Getter
@NoArgsConstructor
public class AstListener extends IcssBaseListener {
  private final Ast ast = new Ast();
  private final ParseTreeProperty<AstNode> nodes = new ParseTreeProperty<>();

  @Override
  public void exitStylesheet(IcssParser.StylesheetContext context) {
    var stylesheet = new Stylesheet();
    for (var variableAssignmentContext : context.variableAssignment()) {
      stylesheet.addChild(nodes.get(variableAssignmentContext));
    }
    for (var rulesetContext : context.ruleset()) {
      stylesheet.addChild(nodes.get(rulesetContext));
    }
    ast.setRoot(stylesheet);
  }

  @Override
  public void exitVariableAssignment(IcssParser.VariableAssignmentContext context) {
    var variableIdentifier = (VariableIdentifier)nodes.get(context.variableIdentifier());
    var expression = (Expression)nodes.get(context.expression());
    var variableAssignment = new VariableAssignment().addChild(variableIdentifier).addChild(expression);
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
    String stringValue = context.getText();
    var booleanLiteral = new BooleanLiteral(stringValue);
    nodes.put(context, booleanLiteral);
  }

  @Override
  public void exitColorLiteral(IcssParser.ColorLiteralContext context) {
    String stringValue = context.getText();
    var colorLiteral = new ColorLiteral(stringValue);
    nodes.put(context, colorLiteral);
  }

  @Override
  public void exitMathExpression(IcssParser.MathExpressionContext context) {
    nodes.put(context, nodes.get(context.additionExpression()));
  }

  @Override
  public void exitAdditionExpression(IcssParser.AdditionExpressionContext context) {
    var left = (MathExpression)nodes.get(context.multiplicationExpression(0));
    for (int i = 1; i < context.multiplicationExpression().size(); ++i) {
      var right = (MathExpression)nodes.get(context.multiplicationExpression(i));
      var operator = context.getChild(2 * i - 1).getText();
      left = switch (operator) {
        case "+" -> new BinaryAddition(left, right);
        case "-" -> new BinarySubtraction(left, right);
        default -> throw new RuntimeException("Unexpected operator: %s".formatted(operator));
      };
    }
    nodes.put(context, left);
  }

  @Override
  public void exitMultiplicationExpression(IcssParser.MultiplicationExpressionContext context) {
    var left = (MathExpression)nodes.get(context.unaryExpression(0));
    for (int i = 1; i < context.unaryExpression().size(); ++i) {
      var right = (MathExpression)nodes.get(context.unaryExpression(i));
      var operator = context.getChild(2 * i - 1).getText();
      if (!operator.equals("*")) {
        throw new RuntimeException("Unexpected operator: %s".formatted(operator));
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
    MathExpression operand = (MathExpression)nodes.get(context.unaryExpression());
    String operator = context.getChild(0).getText();
    operand = switch (operator) {
      case "+" -> new UnaryPlus(operand);
      case "-" -> new UnaryMinus(operand);
      default -> throw new RuntimeException("Unexpected operator: %s".formatted(operator));
    };
    nodes.put(context, operand);
  }

  @Override
  public void exitPrimaryExpression(IcssParser.PrimaryExpressionContext context) {
    if (context.numericLiteral() != null) {
      var numericLiteralContext = context.numericLiteral();
      NumericLiteral numericLiteral = null;
      var stringValue = "";
      if (numericLiteralContext.scalarLiteral() != null) {
        stringValue = numericLiteralContext.scalarLiteral().getText();
        numericLiteral = new ScalarLiteral(stringValue);
      } else if (numericLiteralContext.percentageLiteral() != null) {
        stringValue = numericLiteralContext.percentageLiteral().getText();
        numericLiteral = new PercentageLiteral(stringValue);
      } else if (numericLiteralContext.pixelLiteral() != null) {
        stringValue = numericLiteralContext.pixelLiteral().getText();
        numericLiteral = new PixelLiteral(stringValue);
      } else {
        throw new RuntimeException("Unexpected numeric literal: %s".formatted(context.getText()));
      }
      nodes.put(context, numericLiteral);
    } else if (context.variableIdentifier() != null) {
      var variableIdentifier = (VariableIdentifier)nodes.get(context.variableIdentifier());
      nodes.put(context, variableIdentifier);
    }
  }

  @Override
  public void exitRuleset(IcssParser.RulesetContext context) {
    var ruleset = new Ruleset();
    ruleset.addChild(nodes.get(context.selector()));
    for (var variableAssignmentContext : context.variableAssignment()) {
      ruleset.addChild(nodes.get(variableAssignmentContext));
    }
    for (var declarationContext : context.declaration()) {
      ruleset.addChild(nodes.get(declarationContext));
    }
    nodes.put(context, ruleset);
  }

  @Override
  public void exitSelector(IcssParser.SelectorContext context) {
    if (context.elementSelector() != null) {
      nodes.put(context, nodes.get(context.elementSelector()));
    } else if (context.idSelector() != null) {
      nodes.put(context, nodes.get(context.idSelector()));
    } else if (context.classSelector() != null) {
      nodes.put(context, nodes.get(context.classSelector()));
    }
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
  public void exitDeclaration(IcssParser.DeclarationContext context) {
    var property = (Property)nodes.get(context.property());
    var expression = (Expression)nodes.get(context.expression());
    var declaration = new Declaration().addChild(property).addChild(expression);
    nodes.put(context, declaration);
  }

  @Override
  public void exitProperty(IcssParser.PropertyContext context) {
    String propertyName = context.getText();
    var property = new Property(propertyName);
    nodes.put(context, property);
  }
}
