package nl.han.ica.icss.ast.unused.literals;

import nl.han.ica.icss.ast.unused.Literal;
import java.util.Objects;

public class BooleanLiteral extends Literal {
    public boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }
    public BooleanLiteral(String text) {
        this.value = text.equals("TRUE");
    }
    @Override
    public String getNodeLabel() {
        String textValue = value ? "TRUE" : "FALSE";
        return "Boolean Literal (" + textValue + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BooleanLiteral that = (BooleanLiteral) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
