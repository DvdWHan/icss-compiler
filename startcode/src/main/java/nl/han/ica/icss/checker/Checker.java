package nl.han.ica.icss.checker;

import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.expression.Expression;
import nl.han.ica.icss.ast.expression.math.BinaryExpression;
import nl.han.ica.icss.ast.expression.math.UnaryExpression;
import nl.han.ica.icss.ast.expression.math.operation.*;
import nl.han.ica.icss.ast.literal.BooleanLiteral;
import nl.han.ica.icss.ast.literal.ColorLiteral;
import nl.han.ica.icss.ast.literal.numeric.PercentageLiteral;
import nl.han.ica.icss.ast.literal.numeric.PixelLiteral;
import nl.han.ica.icss.ast.literal.numeric.ScalarLiteral;
import nl.han.ica.icss.ast.variable.VariableAssignment;
import nl.han.ica.icss.ast.variable.VariableIdentifier;

import java.util.*;

import static nl.han.ica.icss.ast.expression.Expression.Type;

@NoArgsConstructor
public class Checker {
  private final Deque<Map<String, Type>> scopes = new ArrayDeque<>();

  public void check(Ast ast) {
    visit(ast.getRoot());
  }

  private Type visit(AstNode node) {
    if (node instanceof Stylesheet stylesheet) {
      return visitStylesheet(stylesheet);
    }
    if (node instanceof VariableAssignment variableAssignment) {
      return visitVariableAssignment(variableAssignment);
    }
    if (node instanceof VariableIdentifier variableIdentifier) {
      return visitVariableIdentifier(variableIdentifier);
    }
    if (node instanceof BinaryAddition binaryAddition) {
      return visitBinary(binaryAddition);
    }
    if (node instanceof BinarySubtraction binarySubtraction) {
      return visitBinary(binarySubtraction);
    }
    if (node instanceof BinaryMultiplication binaryMultiplication) {
      return visitBinary(binaryMultiplication);
    }
    if (node instanceof UnaryPlus unaryPlus) {
      return visitUnary(unaryPlus);
    }
    if (node instanceof UnaryMinus unaryMinus) {
      return visitUnary(unaryMinus);
    }
    if (node instanceof Expression expression) {
      return visitExpression(expression);
    }
    if (node instanceof Ruleset ruleset) {
      return visitRuleset(ruleset);
    }
    if (node instanceof Declaration declaration) {
      return visitDeclaration(declaration);
    }
    for (AstNode child : node.getChildren()) {
      visit(child);
    }
    return Type.UNDEFINED;
  }

  private Type visitStylesheet(Stylesheet stylesheet) {
    enterScope();
    for (AstNode child : stylesheet.getChildren()) {
      visit(child);
    }
    exitScope();
    return Type.UNDEFINED;
  }

  private Type visitVariableAssignment(VariableAssignment variableAssignment) {
    var variableIdentifier = (VariableIdentifier)variableAssignment.getChildren().getFirst();
    var expression = (Expression)variableAssignment.getChildren().get(1);
    Type type = visit(expression);
    declare(variableIdentifier.getIdentifier(), type);
    return Type.UNDEFINED;
  }

  private Type visitVariableIdentifier(VariableIdentifier variableIdentifier) {
    String identifier = variableIdentifier.getIdentifier();
    Type type = resolve(identifier);
    ;
    if (type == Type.UNDEFINED) {
      variableIdentifier.setError("Undefined variable '%s'".formatted(identifier));
    }
    return type;
  }

  private Type visitBinary(BinaryExpression binaryExpression) {
    Type left = visit(binaryExpression.getLeft());
    Type right = visit(binaryExpression.getRight());
    if (left == Type.COLOR || right == Type.COLOR) {
      binaryExpression.setError("Cannot use COLOR in %s operation".formatted(binaryOperator(binaryExpression)));
      return Type.UNDEFINED;
    }
    if (binaryExpression instanceof BinaryAddition || binaryExpression instanceof BinarySubtraction) {
      if (left != right) {
        binaryExpression.setError("Cannot %s %s and %s".formatted(binaryOperator(binaryExpression), left, right));
        return Type.UNDEFINED;
      }
      return left;
    }
    if (binaryExpression instanceof BinaryMultiplication) {
      if (left != Type.SCALAR && right != Type.SCALAR) {
        binaryExpression.setError("Cannot multiply %s and %s: one operand must be scalar".formatted(left, right));
      }
      return (left == Type.SCALAR) ? right : left;
    }
    return Type.UNDEFINED;
  }

  private String binaryOperator(BinaryExpression binaryExpression) {
    return switch (binaryExpression) {
      case BinaryAddition ignored -> "add";
      case BinarySubtraction ignored -> "subtract";
      case BinaryMultiplication ignored -> "multiply";
      default -> "operate on";
    };
  }

  private Type visitUnary(UnaryExpression unaryExpression) {
    Type operand = visit(unaryExpression.getOperand());
    if (operand == Type.COLOR) {
      unaryExpression.setError("Unary %s not allowed on %s".formatted(unaryOperator(unaryExpression), operand));
      return Type.UNDEFINED;
    }
    return operand;
  }

  private String unaryOperator(UnaryExpression unaryExpression) {
    return unaryExpression instanceof UnaryMinus ? "minus" : "plus";
  }

  private Type visitExpression(Expression expression) {
    if (expression instanceof BooleanLiteral) {
      return Type.BOOLEAN;
    }
    if (expression instanceof ColorLiteral) {
      return Type.COLOR;
    }
    if (expression instanceof PercentageLiteral) {
      return Type.PERCENTAGE;
    }
    if (expression instanceof PixelLiteral) {
      return Type.PIXEL;
    }
    if (expression instanceof ScalarLiteral) {
      return Type.SCALAR;
    }
    return visit((AstNode)expression);
  }

  private Type visitRuleset(Ruleset ruleset) {
    enterScope();
    for (AstNode child : ruleset.getChildren()) {
      visit(child);
    }
    exitScope();
    return Type.UNDEFINED;
  }

  private Type visitDeclaration(Declaration declaration) {
    var property = (Property)declaration.getChildren().getFirst();
    var expression = (Expression)declaration.getChildren().get(1);
    Type type = visit(expression);
    String identifier = property.getIdentifier();
    switch (identifier) {
      case "width":
      case "height": {
        if (type != Type.PERCENTAGE && type != Type.PIXEL) {
          declaration.setError("Invalid value for '%s': expected %s, got %s".formatted(
              identifier,
              expectedType(identifier),
              type
          ));
        }
      }
      break;
      case "color":
      case "background-color": {
        if (type != Type.COLOR) {
          declaration.setError("Invalid value for '%s': expected %s, got %s".formatted(
              identifier,
              expectedType(identifier),
              type
          ));
        }
      }
      break;
    }
    return Type.UNDEFINED;
  }

  private String expectedType(String property) {
    return switch (property) {
      case "width", "height" -> "PIXEL or PERCENTAGE";
      case "color", "background-color" -> "COLOR";
      default -> "unknown";
    };
  }

  private void enterScope() {
    scopes.push(new HashMap<>());
  }

  private void exitScope() {
    scopes.pop();
  }

  private void declare(String name, Type type) {
    Objects.requireNonNull(scopes.peek()).put(name, type);
  }

  private Type resolve(String name) {
    for (Map<String, Type> scope : scopes) {
      if (scope.containsKey(name)) {
        return scope.get(name);
      }
    }
    return Type.UNDEFINED;
  }
}
