package nl.han.ica.icss.parser;


import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.han.ica.datastructures.HanStack;
import nl.han.ica.datastructures.IHanStack;
import nl.han.ica.icss.ast.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@Getter
@NoArgsConstructor
public class AstListener extends IcssBaseListener {
  private final Ast ast = new Ast();
  private final IHanStack<AstNode> nodes = new HanStack<>();

  @Override
  public void exitStylesheet(IcssParser.StylesheetContext context) {
    var stylesheet = new Stylesheet();
    addChildrenInstanceOf(stylesheet, Ruleset.class);
    ast.setRoot(stylesheet);
  }

  @Override
  public void exitRuleset(IcssParser.RulesetContext context) {
    var declarations = (Declarations)nodes.pop();
    var selector = (Selector)nodes.pop();
    var ruleset = new Ruleset(selector, declarations);
    nodes.push(ruleset);
  }

  @Override
  public void exitSelector(IcssParser.SelectorContext context) {
    String identifier = context.getText();
    var selector = Selector.of(identifier);
    nodes.push(selector);
  }

  @Override
  public void exitDeclarations(IcssParser.DeclarationsContext context) {
    var declarations = new Declarations();
    addChildrenInstanceOf(declarations, Declaration.class);
    nodes.push(declarations);
  }

  @Override
  public void exitDeclaration(IcssParser.DeclarationContext context) {
    var literal = (Literal<?>)nodes.pop();
    var property = (Property)nodes.pop();
    var declaration = new Declaration(property, literal);
    nodes.push(declaration);
  }

  @Override
  public void exitProperty(IcssParser.PropertyContext context) {
    String propertyName = context.getText();
    var property = new Property(propertyName);
    nodes.push(property);
  }

  @Override
  public void exitLiteral(IcssParser.LiteralContext context) {
    String literalValue = context.getText();
    var literal = Literal.of(literalValue);
    nodes.push(literal);
  }

  /// This method only exists because we have a custom {@code Stack} implementation.
  /// Otherwise, I would have used {@link Collections#reverse(List)}
  private void addChildrenInstanceOf(AstNode parent, Class<?> classInstance) {
    IHanStack<AstNode> children = new HanStack<>();
    // The stack has to be flipped to accommodate for associativity
    // For example, if ElementSelector("p") appears first in the .icss file,
    // I want it to be first in the AST
    while (classInstance.isInstance(nodes.peek())) {
      AstNode child = nodes.pop();
      children.push(child);
    }
    while (classInstance.isInstance(children.peek())) {
      AstNode child = children.pop();
      parent.addChild(child);
    }
  }
}
