package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.IHanStack;
import nl.han.ica.icss.ast.*;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class AstListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private Ast ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHanStack<AstNode> currentContainer;

	public AstListener() {
		ast = new Ast();
		//currentContainer = new HANStack<>();
	}
    public Ast getAST() {
        return ast;
    }
    
}
