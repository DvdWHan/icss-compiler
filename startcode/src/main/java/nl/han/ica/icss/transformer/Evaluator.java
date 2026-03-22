package nl.han.ica.icss.transformer;

import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.expression.Expression;
import nl.han.ica.icss.ast.expression.math.BinaryExpression;
import nl.han.ica.icss.ast.expression.math.UnaryExpression;
import nl.han.ica.icss.ast.expression.math.operation.BinaryMultiplication;
import nl.han.ica.icss.ast.expression.math.operation.UnaryMinus;
import nl.han.ica.icss.ast.expression.math.operation.UnaryPlus;
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
public class Evaluator implements Transformer {
  private final Deque<Map<String, Value<?>>> scopes = new ArrayDeque<>();

  @Override
  public void apply(Ast ast) {
    visit(ast.getRoot());
  }

  private Value<?> visit(AstNode node) {
    if (node instanceof Stylesheet stylesheet) {
      return visitStylesheet(stylesheet);
    }
    if (node instanceof VariableAssignment variableAssignment) {
      return visitVariableAssignment(variableAssignment);
    }
    if (node instanceof BinaryExpression binaryExpression) {
      return visitBinary(binaryExpression);
    }
    if (node instanceof UnaryExpression unaryExpression) {
      return visitUnary(unaryExpression);
    }
    if (node instanceof VariableIdentifier variableIdentifier) {
      return visitVariableIdentifier(variableIdentifier);
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
    return null;
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
    var variableIdentifier = (VariableIdentifier)variableAssignment.getChildren().getFirst();
    var expression = (Expression)variableAssignment.getChildren().get(1);
    Value<?> value = visit(expression);
    scopes.peek().put(variableIdentifier.getIdentifier(), value);
    removeSelf(variableAssignment);
    return null;
  }

  private Value<?> visitVariableIdentifier(VariableIdentifier variableIdentifier) {
    Value<?> value = resolve(variableIdentifier.getIdentifier());
    Expression expression = toExpression(value);
    replaceSelf(variableIdentifier, expression);
    return value;
  }

  private Value<?> visitExpression(Expression expression) {
    if (expression instanceof BooleanLiteral booleanLiteral) {
      return new Value<>(Type.BOOLEAN, Boolean.parseBoolean(booleanLiteral.getStringValue()));
    }
    if (expression instanceof ColorLiteral colorLiteral) {
      return new Value<>(Type.COLOR, colorLiteral.getStringValue());
    }
    if (expression instanceof PercentageLiteral percentageLiteral) {
      String stringValue = percentageLiteral.getStringValue();
      int value = Integer.parseInt(stringValue.substring(0, stringValue.length() - 1));
      return new Value<>(Type.PERCENTAGE, value);
    }
    if (expression instanceof PixelLiteral pixelLiteral) {
      String stringValue = pixelLiteral.getStringValue();
      int value = Integer.parseInt(stringValue.substring(0, stringValue.length() - 2));
      return new Value<>(Type.PIXEL, value);
    }
    if (expression instanceof ScalarLiteral scalarLiteral) {
      return new Value<>(Type.SCALAR, Integer.parseInt(scalarLiteral.getStringValue()));
    }
    return visit((AstNode)expression);
  }

  private Value<?> visitBinary(BinaryExpression binaryExpression) {
    Value<?> left = visit(binaryExpression.getLeft());
    Value<?> right = visit(binaryExpression.getRight());
    Value<?> result = computeBinary(binaryExpression, left, right);
    Expression expression = toExpression(result);
    replaceSelf(binaryExpression, expression);
    return result;
  }

  private Value<?> computeBinary(BinaryExpression binaryExpression, Value<?> left, Value<?> right) {
    return new Value<>(left.type == Type.SCALAR ? right.type : left.type, binaryExpression.evaluate());
  }

  private Value<?> visitUnary(UnaryExpression unaryExpression) {
    Value<?> value = visit(unaryExpression.getOperand());
    Value<?> result = computeUnary(unaryExpression, value);
    Expression expression = toExpression(result);
    replaceSelf(unaryExpression, expression);
    return result;
  }

  private Value<?> computeUnary(UnaryExpression unaryExpression, Value<?> operand) {
    return new Value<>(operand.type, unaryExpression.evaluate());
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
    var expression = (Expression)declaration.getChildren().get(1);
    Value<?> value = visit(expression);
    declaration.replaceChild(expression, toExpression(value));
    return null;
  }

  private Expression toExpression(Value<?> value) {
    return switch (value.type) {
      case BOOLEAN -> new BooleanLiteral(value.value.toString());
      case COLOR -> new ColorLiteral((String)value.value);
      case PERCENTAGE -> new PercentageLiteral(value.value + "%");
      case PIXEL -> new PixelLiteral(value.value + "px");
      case SCALAR -> new ScalarLiteral(value.value.toString());
      default -> throw new RuntimeException();
    };
  }

  private void removeSelf(AstNode node) {
    node.getParent().removeChild(node);
  }

  private void replaceSelf(AstNode self, AstNode with) {
    self.getParent().replaceChild(self, with);
  }

  private void enterScope() {
    scopes.push(new HashMap<>());
  }

  private void exitScope() {
    scopes.pop();
  }

  private Value<?> resolve(String identifier) {
    for (var scope : scopes) {
      if (scope.containsKey(identifier)) {
        return scope.get(identifier);
      }
    }
    throw new RuntimeException("Cannot resolve variable '%s'".formatted(identifier));
  }

  private record Value<T>(Type type, T value) {}
}
