package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.Ast;

public interface Transform {
    void apply(Ast ast);
}
