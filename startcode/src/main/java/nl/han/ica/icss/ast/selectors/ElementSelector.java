package nl.han.ica.icss.ast.selectors;

import nl.han.ica.icss.ast.Selector;

import java.util.Objects;

public class ElementSelector extends Selector {
    public String element;

    public ElementSelector(String element) {
        this.element = element;
    }

    public String getNodeLabel() {
        return "ElementSelector " + element;
    }
    public String toString() {
        return element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ElementSelector that = (ElementSelector) o;
        return Objects.equals(element, that.element);
    }

    @Override
    public int hashCode() {

        return Objects.hash(element);
    }
}
