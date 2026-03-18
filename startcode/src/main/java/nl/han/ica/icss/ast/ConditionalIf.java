package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.Objects;

public class ConditionalIf extends AstNode {


    public Expression conditionalExpression;
    public ArrayList<AstNode> body = new ArrayList<>();
    public ConditionalElse conditionalElse;

    public ConditionalIf() { }

    public ConditionalIf(Expression conditionalExpression, ArrayList<AstNode> body) {

        this.conditionalExpression = conditionalExpression;
        this.body = body;
    }
    public ConditionalIf(Expression conditionalExpression, ArrayList<AstNode> body, ConditionalElse conditionalElse) {

        this.conditionalExpression = conditionalExpression;
        this.body = body;
        this.conditionalElse = conditionalElse;
    }

    @Override
    public String getNodeLabel() {
        return "If_Clause";
    }
    @Override
    public ArrayList<AstNode> getChildren() {
        ArrayList<AstNode> children = new ArrayList<>();
        children.add(conditionalExpression);
        children.addAll(body);
        if (conditionalElse != null)
            children.add(conditionalElse);

        return children;
    }

    @Override
    public AstNode addChild(AstNode child) {
        if(child instanceof Expression)
            conditionalExpression  = (Expression) child;
        else if (child instanceof ConditionalElse)
            conditionalElse = (ConditionalElse) child;
        else
            body.add(child);

        return this;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ConditionalIf conditionalIf = (ConditionalIf) o;
        if (this.conditionalElse == null)
            return Objects.equals(conditionalExpression, conditionalIf.getConditionalExpression()) &&
                   Objects.equals(body, conditionalIf.body);
        else
            return Objects.equals(conditionalExpression, conditionalIf.getConditionalExpression()) &&
                   Objects.equals(body, conditionalIf.body) &&
                   Objects.equals(conditionalElse, conditionalIf.conditionalElse);

    }

    @Override
    public int hashCode() {
        return Objects.hash(conditionalExpression, body, conditionalElse);
    }

    public Expression getConditionalExpression() {
        return conditionalExpression;
    }
    public ConditionalElse getElseClause() { return conditionalElse; }
}
