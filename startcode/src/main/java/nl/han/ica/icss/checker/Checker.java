package nl.han.ica.icss.checker;

import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.expression.VariableIdentifier;
import nl.han.ica.icss.ast.expression.literal.BooleanLiteral;
import nl.han.ica.icss.ast.expression.literal.ColorLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.PercentageLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.PixelLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.ScalarLiteral;
import nl.han.ica.icss.ast.expression.math.BinaryExpression;
import nl.han.ica.icss.ast.expression.math.UnaryExpression;
import nl.han.ica.icss.ast.expression.math.binary.BinaryAddition;
import nl.han.ica.icss.ast.expression.math.binary.BinaryMultiplication;
import nl.han.ica.icss.ast.expression.math.binary.BinarySubtraction;

import java.util.*;

import static nl.han.ica.icss.ast.Expression.Type;

@NoArgsConstructor
public class Checker {
  private final Deque<Map<String, Type>> scopes = new ArrayDeque<>();

  public void check(Ast ast) {
    visit(ast.getRoot());
  }

  private Type visit(AstNode node) {
    return switch (node) {
      case Stylesheet stylesheet -> visitStylesheet(stylesheet);
      case VariableAssignment variableAssignment -> visitVariableAssignment(variableAssignment);
      case VariableIdentifier variableIdentifier -> visitVariableIdentifier(variableIdentifier);
      case BinaryExpression binaryExpression -> visitBinaryExpression(binaryExpression);
      case UnaryExpression unaryExpression -> visitUnaryExpression(unaryExpression);
      case Expression expression -> visitExpression(expression);
      case Ruleset ruleset -> visitRuleset(ruleset);
      case Declaration declaration -> visitDeclaration(declaration);
      default -> Type.UNDEFINED;
    };
  }

  private Type visitStylesheet(Stylesheet stylesheet) {
    enterScope();
    for (VariableAssignment variableAssignment : stylesheet.getVariableAssignments()) {
      visit(variableAssignment);
    }
    for (Ruleset ruleset : stylesheet.getRulesets()) {
      visit(ruleset);
    }
    exitScope();
    return Type.UNDEFINED;
  }

  private Type visitVariableAssignment(VariableAssignment variableAssignment) {
    Type type = visit(variableAssignment.getExpression());
    declare(variableAssignment.getIdentifier(), type);
    return Type.UNDEFINED;
  }

  private Type visitVariableIdentifier(VariableIdentifier variableIdentifier) {
    String identifier = variableIdentifier.getIdentifier();
    Type type = resolve(identifier);
    if (type == Type.UNDEFINED) {
      variableIdentifier.setError("Undefined variable '%s'".formatted(identifier));
    }
    return type;
  }

  private Type visitBinaryExpression(BinaryExpression binaryExpression) {
    Type left = visit(binaryExpression.getLeft());
    Type right = visit(binaryExpression.getRight());
    if (left == Type.COLOR || right == Type.COLOR) {
      attachError(binaryExpression, "Incompatible types", "%s and %s".formatted(left, right), "SCALAR, PIXEL, or PERCENTAGE");
      return Type.UNDEFINED;
    }
    if (binaryExpression instanceof BinaryAddition || binaryExpression instanceof BinarySubtraction) {
      if (left != right) {
        attachError(binaryExpression, "Incompatible types", "%s and %s".formatted(left, right), "both operands to be the same type"
        );
        return Type.UNDEFINED;
      }
      return left;
    }
    if (binaryExpression instanceof BinaryMultiplication) {
      if (left != Type.SCALAR && right != Type.SCALAR) {
        attachError(binaryExpression, "Incompatible types", "%s and %s".formatted(left, right), "at least one SCALAR");
        return Type.UNDEFINED;
      }
      return left == Type.SCALAR ? right : left;
    }
    return Type.UNDEFINED;
  }

  private Type visitUnaryExpression(UnaryExpression unaryExpression) {
    Type operand = visit(unaryExpression.getOperand());
    if (operand == Type.COLOR) {
      attachError(unaryExpression, "Incompatible type", operand.name(), "SCALAR, PIXEL, or PERCENTAGE");
      return Type.UNDEFINED;
    }
    return operand;
  }

  private Type visitExpression(Expression expression) {
    return switch (expression) {
      case BooleanLiteral ignored -> Type.BOOLEAN;
      case ColorLiteral ignored -> Type.COLOR;
      case PixelLiteral ignored -> Type.PIXEL;
      case PercentageLiteral ignored -> Type.PERCENTAGE;
      case ScalarLiteral ignored -> Type.SCALAR;
      default -> visit(expression);
    };
  }

  private Type visitRuleset(Ruleset ruleset) {
    enterScope();
    for (VariableAssignment variableAssignment : ruleset.getVariableAssignments()) {
      visit(variableAssignment);
    }
    for (Declaration declaration : ruleset.getDeclarations()) {
      visit(declaration);
    }
    exitScope();
    return Type.UNDEFINED;
  }

  private Type visitDeclaration(Declaration declaration) {
    Type type = visit(declaration.getExpression());
    Property property = declaration.getProperty();
    switch (property.getIdentifier()) {
      case "width", "height" -> {
        if (type != Type.PIXEL && type != Type.PERCENTAGE) {
          attachError(declaration, "Incompatible type for width/height", type.name(), "PIXEL or PERCENTAGE");
        }
      }
      case "color", "background-color" -> {
        if (type != Type.COLOR) {
          attachError(declaration, "Incompatible type for color/background-color", type.name(), "COLOR");
        }
      }
    }
    return Type.UNDEFINED;
  }

  private void attachError(AstNode node, String wrong, String found, String expected) {
    node.setError("%s: found %s, expected %s".formatted(wrong, found, expected));
  }

  private void enterScope() {
    scopes.push(new HashMap<>());
  }

  private void exitScope() {
    scopes.pop();
  }

  private void declare(String identifier, Type type) {
    Objects.requireNonNull(scopes.peek()).put(identifier, type);
  }

  private Type resolve(String name) {
    for (var scope : scopes) {
      if (scope.containsKey(name)) {
        return scope.get(name);
      }
    }
    return Type.UNDEFINED;
  }
}
