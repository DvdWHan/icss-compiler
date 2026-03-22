package nl.han.ica.icss.ast;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class Ruleset extends AstNode {
  @Getter private final Selector selector;
  @Getter private final List<VariableAssignment> variableAssignments = new ArrayList<>();
  @Getter private final List<Declaration> declarations = new ArrayList<>();

  public Ruleset(Selector selector) {
    this.selector = selector;
  }

  public void addVariableAssignment(VariableAssignment variableAssignment) {
    variableAssignments.add(variableAssignment);
    addChild(variableAssignment);
  }

  public void addDeclaration(Declaration declaration) {
    declarations.add(declaration);
    addChild(declaration);
  }
}
