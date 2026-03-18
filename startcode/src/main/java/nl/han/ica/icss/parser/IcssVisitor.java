// Generated from src/main/antlr4/nl/han/ica/icss/parser/Icss.g4 by ANTLR 4.13.2
package nl/han/ica/icss/parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link IcssParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface IcssVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link IcssParser#stylesheet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylesheet(IcssParser.StylesheetContext ctx);
	/**
	 * Visit a parse tree produced by {@link IcssParser#ruleset}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleset(IcssParser.RulesetContext ctx);
	/**
	 * Visit a parse tree produced by {@link IcssParser#selector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelector(IcssParser.SelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link IcssParser#elementSelector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementSelector(IcssParser.ElementSelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link IcssParser#idSelector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdSelector(IcssParser.IdSelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link IcssParser#classSelector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassSelector(IcssParser.ClassSelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link IcssParser#declarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarations(IcssParser.DeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link IcssParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(IcssParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link IcssParser#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(IcssParser.PropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link IcssParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(IcssParser.ValueContext ctx);
}