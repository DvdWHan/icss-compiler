package nl.han.ica.icss.ast.unused;

import nl.han.ica.icss.ast.AstNode;

import java.util.List;
import java.util.Objects;

public class VariableReference implements Expression {

	public String name;
	
	public VariableReference(String name) {
		super();
		this.name = name;
	}

  @Override
  public List<AstNode> getChildren() {
    return List.of();
  }

  @Override
  public AstNode addChild(AstNode child) {
    return this;
  }

  @Override
	public String getNodeLabel() {
		return "VariableReference (" + name + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		VariableReference that = (VariableReference) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {

		return Objects.hash(name);
	}
}
