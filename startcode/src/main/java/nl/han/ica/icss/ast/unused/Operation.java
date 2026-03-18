package nl.han.ica.icss.ast.unused;

import nl.han.ica.icss.ast.AstNode;

import java.util.ArrayList;
import java.util.List;

public abstract class Operation extends Expression {

    public Expression lhs;
    public Expression rhs;

    @Override
    public List<AstNode> getChildren() {
        ArrayList<AstNode> children = new ArrayList<>();
        if(lhs != null)
            children.add(lhs);
        if(rhs != null)
            children.add(rhs);
        return children;
    }

    @Override
    public AstNode addChild(AstNode child) {
        if(lhs == null) {
            lhs = (Expression) child;
        } else if(rhs == null) {
            rhs = (Expression) child;
        }
        return this;
    }
}
