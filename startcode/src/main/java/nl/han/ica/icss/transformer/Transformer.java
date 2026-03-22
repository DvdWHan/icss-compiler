package nl.han.ica.icss.transformer;

import nl.han.ica.icss.ast.Ast;

public interface Transformer {
    void apply(Ast ast);
}
