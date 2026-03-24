package nl.han.ica.icss.transformer;

import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.Expression.Type;
import nl.han.ica.icss.ast.expression.BinaryExpression;
import nl.han.ica.icss.ast.expression.UnaryExpression;
import nl.han.ica.icss.ast.expression.Value;
import nl.han.ica.icss.ast.expression.VariableIdentifier;
import nl.han.ica.icss.ast.expression.literal.BooleanLiteral;
import nl.han.ica.icss.ast.expression.literal.ColorLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.PercentageLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.PixelLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.ScalarLiteral;

import java.util.*;

@NoArgsConstructor
public class Evaluator implements Transformer {
  private final Deque<Map<String, Value<?>>> scopes = new ArrayDeque<>();

  @Override
  public void apply(Ast ast) {
    visit(ast.getRoot());
  }

  private Optional<Value<?>> visit(AstNode<?> node) {
    return switch (node) {
      case Stylesheet stylesheet -> visitStylesheet(stylesheet);
      case VariableAssignment variableAssignment -> visitVariableAssignment(variableAssignment);
      case VariableIdentifier variableIdentifier -> visitVariableIdentifier(variableIdentifier);
      case BinaryExpression binaryExpression -> visitBinaryExpression(binaryExpression);
      case UnaryExpression unaryExpression -> visitUnaryExpression(unaryExpression);
      case Expression expression -> visitExpression(expression);
      case Ruleset ruleset -> visitRuleset(ruleset);
      case Body body -> visitBody(body);
      case Declaration declaration -> visitDeclaration(declaration);
      case ConditionalStatement conditionalStatement -> visitConditionalStatement(conditionalStatement);
      default -> Optional.empty();
    };
  }

  private Optional<Value<?>> visitStylesheet(Stylesheet stylesheet) {
    scoped(() -> visitChildren(stylesheet));
    return Optional.empty();
  }

  private Optional<Value<?>> visitVariableAssignment(VariableAssignment variableAssignment) {
    Value<?> value = visit(variableAssignment.getExpression()).orElseThrow();
    declare(variableAssignment.getIdentifier(), value);
    variableAssignment.remove();
    return Optional.empty();
  }

  private Optional<Value<?>> visitVariableIdentifier(VariableIdentifier variableIdentifier) {
    Value<?> value = resolve(variableIdentifier.getIdentifier());
    Expression replacement = toExpression(value);
    variableIdentifier.replaceWith(replacement);
    return Optional.of(value);
  }

  private Optional<Value<?>> visitBinaryExpression(BinaryExpression binaryExpression) {
    Value<?> left = visit(binaryExpression.getLeft()).orElseThrow();
    Value<?> right = visit(binaryExpression.getRight()).orElseThrow();
    Value<?> result = binaryExpression.evaluate(left, right);
    binaryExpression.replaceWith(toExpression(result));
    return Optional.of(result);
  }

  private Optional<Value<?>> visitUnaryExpression(UnaryExpression unaryExpression) {
    Value<?> operand = visit(unaryExpression.getOperand()).orElseThrow();
    Value<?> result = unaryExpression.evaluate(operand);
    unaryExpression.replaceWith(toExpression(result));
    return Optional.of(result);
  }

  private Optional<Value<?>> visitExpression(Expression expression) {
    return Optional.of(switch (expression) {
      case BooleanLiteral booleanLiteral -> new Value<>(Type.BOOLEAN, booleanLiteral.getValue());
      case ColorLiteral colorLiteral -> new Value<>(Type.COLOR, colorLiteral.getValue());
      case PercentageLiteral percentageLiteral -> new Value<>(Type.PERCENTAGE, percentageLiteral.getValue());
      case PixelLiteral pixelLiteral -> new Value<>(Type.PIXEL, pixelLiteral.getValue());
      case ScalarLiteral scalarLiteral -> new Value<>(Type.SCALAR, scalarLiteral.getValue());
      default -> visit(expression).orElseThrow();
    });
  }

  private Optional<Value<?>> visitRuleset(Ruleset ruleset) {
    visitChildren(ruleset);
    return Optional.empty();
  }

  private Optional<Value<?>> visitBody(Body body) {
    scoped(() -> visitChildren(body));
    return Optional.empty();
  }

  private Optional<Value<?>> visitDeclaration(Declaration declaration) {
    Expression expression = declaration.getExpression();
    Optional<Value<?>> value = visit(expression);
    declaration.setExpression(toExpression(value.orElseThrow()));
    return Optional.empty();
  }

  private Optional<Value<?>> visitConditionalStatement(ConditionalStatement conditionalStatement) {
    boolean result = evaluateCondition(conditionalStatement);
    Body parent = (Body)conditionalStatement.getParent();
    Body chosen = result ? conditionalStatement.getIfBody() : conditionalStatement.getElseBody();
    Body unchosen = result ? conditionalStatement.getElseBody() : conditionalStatement.getIfBody();
    hoistBodyIntoParent(chosen, parent);
    removeBody(unchosen);
    conditionalStatement.remove();
    return Optional.empty();
  }

  private boolean evaluateCondition(ConditionalStatement conditionalStatement) {
    return (boolean)visit(conditionalStatement.getCondition()).orElseThrow().value();
  }

  private void hoistBodyIntoParent(Body source, Body target) {
    if (source == null) {
      return;
    }
    scoped(() -> {
      int index = target.getChildren().size();
      for (AstNode<?> child : new ArrayList<>(source.getChildren())) {
        child.remove();
        target.addChild(index++, child);
        visit(child);
      }
    });
  }

  private void visitChildren(AstNode<?> node) {
    for (AstNode<?> child : new ArrayList<>(node.getChildren())) {
      visit(child);
    }
  }

  private void removeBody(Body body) {
    if (body != null) {
      body.remove();
    }
  }

  private Expression toExpression(Value<?> value) {
    return switch (value.type()) {
      case BOOLEAN -> new BooleanLiteral((boolean)value.value());
      case COLOR -> new ColorLiteral((String)value.value());
      case PERCENTAGE -> new PercentageLiteral((int)value.value());
      case PIXEL -> new PixelLiteral((int)value.value());
      case SCALAR -> new ScalarLiteral((int)value.value());
      default -> throw new RuntimeException();
    };
  }

  private void scoped(Runnable runnable) {
    enterScope();
    runnable.run();
    exitScope();
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
