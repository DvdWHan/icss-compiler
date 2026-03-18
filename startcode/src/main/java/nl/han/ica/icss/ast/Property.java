package nl.han.ica.icss.ast;

public class Property extends ASTNode {

    public String name;

    public Property() {
        super();
        name = "undefined";
    }
    public Property(String name) {
        super();
        this.name = name;
    }

    @Override
    public String getNodeLabel() {
        return "Property: (" + name + ")";
    }
}
