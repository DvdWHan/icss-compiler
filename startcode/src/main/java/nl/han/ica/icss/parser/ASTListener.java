package nl.han.ica.icss.parser;

import lombok.Getter;
import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.generated.ICSSParser;
import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.ASTNode;

public class ASTListener extends nl.han.ica.generated.ICSSBaseListener {
  @Getter private final AST ast = new AST();
  private final IHANStack<ASTNode> currentContainer = new HANStack<>();

  @Override
  public void exitStylesheet(ICSSParser.StylesheetContext context) {
    
  }

  @Override
  public void exitRuleset(ICSSParser.RulesetContext context) {
    
  }

  @Override
  public void exitSelector(ICSSParser.SelectorContext context) {
    
  }

  @Override
  public void exitElementSelector(ICSSParser.ElementSelectorContext context) {
    
  }

  @Override
  public void exitIdSelector(ICSSParser.IdSelectorContext context) {
    
  }

  @Override
  public void exitClassSelector(ICSSParser.ClassSelectorContext context) {
    
  }

  @Override
  public void exitDeclarations(ICSSParser.DeclarationsContext context) {
    
  }

  @Override
  public void exitDeclaration(ICSSParser.DeclarationContext context) {
    
  }

  @Override
  public void exitProperty(ICSSParser.PropertyContext context) {
    
  }

  @Override
  public void exitValue(ICSSParser.ValueContext context) {
    
  }
}
