package nl.han.ica.icss.transformer;

import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.Expression.Type;
import nl.han.ica.icss.ast.expression.VariableIdentifier;
import nl.han.ica.icss.ast.expression.literal.BooleanLiteral;
import nl.han.ica.icss.ast.expression.literal.ColorLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.PercentageLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.PixelLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.ScalarLiteral;
import nl.han.ica.icss.ast.expression.math.BinaryExpression;
import nl.han.ica.icss.ast.expression.math.UnaryExpression;
import nl.han.ica.icss.ast.expression.math.Value;
import nl.han.ica.icss.ast.expression.math.binary.BinaryAddition;

import java.util.*;

@NoArgsConstructor
public class Evaluator implements Transformer {
  private final Deque<Map<String, Value<?>>> scopes = new ArrayDeque<>();

  @Override
  public void apply(Ast ast) {
    visit(ast.getRoot());
  }

  private Value<?> visit(AstNode node) {
    return switch (node) {
      case Stylesheet stylesheet -> visitStylesheet(stylesheet);
      case VariableAssignment variableAssignment -> visitVariableAssignment(variableAssignment);
      case VariableIdentifier variableIdentifier -> visitVariableIdentifier(variableIdentifier);
      case BinaryExpression binaryExpression -> visitBinaryExpression(binaryExpression);
      case UnaryExpression unaryExpression -> visitUnaryExpression(unaryExpression);
      case Expression expression -> visitExpression(expression);
      case Ruleset ruleset -> visitRuleset(ruleset);
      case Declaration declaration -> visitDeclaration(declaration);
      default -> null;
    };
  }

  private Value<?> visitStylesheet(Stylesheet stylesheet) {
    enterScope();
    for (AstNode child : new ArrayList<>(stylesheet.getChildren())) {
      visit(child);
    }
    exitScope();
    return null;
  }

  private Value<?> visitVariableAssignment(VariableAssignment variableAssignment) {
    Value<?> value = visit(variableAssignment.getExpression());
    declare(variableAssignment.getIdentifier(), value);
    variableAssignment.remove();
    return null;
  }

  private Value<?> visitVariableIdentifier(VariableIdentifier variableIdentifier) {
    Value<?> value = resolve(variableIdentifier.getIdentifier());
    Expression replacement = toExpression(value);
    variableIdentifier.replaceWith(replacement);
    return value;
  }

  private Value<?> visitBinaryExpression(BinaryExpression binaryExpression) {
    Value<?> left = visit(binaryExpression.getLeft());
    Value<?> right = visit(binaryExpression.getRight());
    Value<?> result = binaryExpression.evaluate(left, right);
    binaryExpression.replaceWith(toExpression(result));
    return result;
  }

  private Value<?> visitUnaryExpression(UnaryExpression unaryExpression) {
    Value<?> operand = visit(unaryExpression.getOperand());
    Value<?> result = unaryExpression.evaluate(operand);
    unaryExpression.replaceWith(toExpression(result));
    return result;
  }

  private Value<?> visitExpression(Expression expression) {
    return switch (expression) {
      case BooleanLiteral booleanLiteral -> new Value<>(Type.BOOLEAN, booleanLiteral.getValue());
      case ColorLiteral colorLiteral -> new Value<>(Type.COLOR, colorLiteral.getValue());
      case PercentageLiteral percentageLiteral -> new Value<>(Type.PERCENTAGE, percentageLiteral.getValue());
      case PixelLiteral pixelLiteral -> new Value<>(Type.PIXEL, pixelLiteral.getValue());
      case ScalarLiteral scalarLiteral -> new Value<>(Type.SCALAR, scalarLiteral.getValue());
      default -> visit((AstNode)expression);
    };
  }

  private Value<?> visitRuleset(Ruleset ruleset) {
    enterScope();
    for (AstNode child : new ArrayList<>(ruleset.getChildren())) {
      visit(child);
    }
    exitScope();
    return null;
  }

  private Value<?> visitDeclaration(Declaration declaration) {
    Expression expression = declaration.getExpression();
    Value<?> value = visit(expression);
    declaration.setExpression(toExpression(value));
    return null;
  }

  private Expression toExpression(Value<?> value) {
    return switch (value.type()) {
      case BOOLEAN -> new BooleanLiteral((boolean) value.value());
      case COLOR -> new ColorLiteral((String) value.value());
      case PERCENTAGE -> new PercentageLiteral((int) value.value());
      case PIXEL -> new PixelLiteral((int) value.value());
      case SCALAR -> new ScalarLiteral((int) value.value());
      default -> throw new RuntimeException();
    };
  }

  private void enterScope() {
    scopes.push(new HashMap<>());
  }

  private void exitScope() {
    scopes.pop();
  }

  private void declare(String name, Value<?> value) {
    Objects.requireNonNull(scopes.peek()).put(name, value);
  }

  private Value<?> resolve(String identifier) {
    for (var scope : scopes) {
      if (scope.containsKey(identifier)) {
        return scope.get(identifier);
      }
    }
    throw new RuntimeException("Undefined variable '%s'".formatted(identifier));
  }
}
