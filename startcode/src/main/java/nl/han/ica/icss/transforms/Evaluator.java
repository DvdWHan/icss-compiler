package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.IHanLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.Literal;

import java.util.HashMap;

public class Evaluator implements Transform {

    private IHanLinkedList<HashMap<String, Literal>> variableValues;

    public Evaluator() {
        //variableValues = new HANLinkedList<>();
    }

    @Override
    public void apply(Ast ast) {
        //variableValues = new HANLinkedList<>();

    }

    
}
