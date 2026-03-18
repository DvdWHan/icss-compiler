package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.Objects;

public class Ruleset extends AstNode {
	
	public ArrayList<Selector> selectors = new ArrayList<>();
	public ArrayList<AstNode> body = new ArrayList<>();

    public Ruleset() { }

    public Ruleset(Selector selector, ArrayList<AstNode> body) {

    	this.selectors = new ArrayList<>();
    	this.selectors.add(selector);
    	this.body = body;
    }

    @Override
	public String getNodeLabel() {
		return "Stylerule";
	}
	@Override
	public ArrayList<AstNode> getChildren() {
		ArrayList<AstNode> children = new ArrayList<>();
		children.addAll(selectors);
		children.addAll(body);

		return children;
	}

    @Override
    public AstNode addChild(AstNode child) {
		if(child instanceof Selector)
			selectors.add((Selector) child);
		else
        	body.add(child);

		return this;
    }
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Ruleset ruleset = (Ruleset) o;
		return Objects.equals(selectors, ruleset.selectors) &&
           Objects.equals(body, ruleset.body);
	}

	@Override
	public int hashCode() {
		return Objects.hash(selectors, body);
	}
}
