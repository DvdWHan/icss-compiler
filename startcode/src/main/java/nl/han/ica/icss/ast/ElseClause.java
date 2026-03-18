package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.Objects;

public class ElseClause extends AstNode {

    public ArrayList<AstNode> body = new ArrayList<>();

    public ElseClause() { }

    public ElseClause(ArrayList<AstNode> body) {

        this.body = body;
    }

    @Override
    public String getNodeLabel() {
        return "Else_Clause";
    }
    @Override
    public ArrayList<AstNode> getChildren() {
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
        ElseClause ElseClause = (ElseClause) o;
        return Objects.equals(body, ElseClause.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }



}
