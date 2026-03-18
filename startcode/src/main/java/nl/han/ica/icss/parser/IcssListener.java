// Generated from src/main/antlr4/nl/han/ica/icss/parser/Icss.g4 by ANTLR 4.13.2
package nl/han/ica/icss/parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link IcssParser}.
 */
public interface IcssListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link IcssParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void enterStylesheet(IcssParser.StylesheetContext ctx);
	/**
	 * Exit a parse tree produced by {@link IcssParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void exitStylesheet(IcssParser.StylesheetContext ctx);
	/**
	 * Enter a parse tree produced by {@link IcssParser#ruleset}.
	 * @param ctx the parse tree
	 */
	void enterRuleset(IcssParser.RulesetContext ctx);
	/**
	 * Exit a parse tree produced by {@link IcssParser#ruleset}.
	 * @param ctx the parse tree
	 */
	void exitRuleset(IcssParser.RulesetContext ctx);
	/**
	 * Enter a parse tree produced by {@link IcssParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterSelector(IcssParser.SelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link IcssParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitSelector(IcssParser.SelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link IcssParser#elementSelector}.
	 * @param ctx the parse tree
	 */
	void enterElementSelector(IcssParser.ElementSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link IcssParser#elementSelector}.
	 * @param ctx the parse tree
	 */
	void exitElementSelector(IcssParser.ElementSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link IcssParser#idSelector}.
	 * @param ctx the parse tree
	 */
	void enterIdSelector(IcssParser.IdSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link IcssParser#idSelector}.
	 * @param ctx the parse tree
	 */
	void exitIdSelector(IcssParser.IdSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link IcssParser#classSelector}.
	 * @param ctx the parse tree
	 */
	void enterClassSelector(IcssParser.ClassSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link IcssParser#classSelector}.
	 * @param ctx the parse tree
	 */
	void exitClassSelector(IcssParser.ClassSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link IcssParser#declarations}.
	 * @param ctx the parse tree
	 */
	void enterDeclarations(IcssParser.DeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link IcssParser#declarations}.
	 * @param ctx the parse tree
	 */
	void exitDeclarations(IcssParser.DeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link IcssParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(IcssParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link IcssParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(IcssParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link IcssParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(IcssParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link IcssParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(IcssParser.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link IcssParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(IcssParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link IcssParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(IcssParser.ValueContext ctx);
}