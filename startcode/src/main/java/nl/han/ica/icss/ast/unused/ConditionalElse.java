package nl.han.ica.icss.ast.unused;

import nl.han.ica.icss.ast.AstNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConditionalElse implements AstNode {

    public ArrayList<AstNode> body = new ArrayList<>();

    public ConditionalElse() { }

    public ConditionalElse(ArrayList<AstNode> body) {

        this.body = body;
    }

    @Override
    public String getNodeLabel() {
        return "Else_Clause";
    }
    @Override
    public List<AstNode> getChildren() {
        ArrayList<AstNode> children = new ArrayList<>();
        children.addAll(body);

        return children;
    }

    @Override
    public AstNode addChild(AstNode child) {

        body.add(child);

        return this;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ConditionalElse ConditionalElse = (ConditionalElse) o;
        return Objects.equals(body, ConditionalElse.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }



}
