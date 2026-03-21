package nl.han.ica.icss;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.han.ica.icss.ast.Ast;
import nl.han.ica.icss.checker.Checker;
import nl.han.ica.icss.checker.SemanticError;
import nl.han.ica.icss.generator.Generator;
import nl.han.ica.icss.parser.*;
import nl.han.ica.icss.transforms.Evaluator;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@Getter
@NoArgsConstructor
public class Pipeline implements ANTLRErrorListener {
  private Ast ast = null;
  private boolean parsed = false;
  private boolean checked = false;
  private boolean transformed = false;
  private final List<String> errors = new ArrayList<>();

  public void parseString(String input) {

    //Lex (with Antlr's generated lexer)
    CharStream inputStream = CharStreams.fromString(input);
    IcssLexer lexer = new IcssLexer(inputStream);
    lexer.removeErrorListeners();
    lexer.addErrorListener(this);
    errors.clear();
    try {
      CommonTokenStream tokens = new CommonTokenStream(lexer);

      //Parse (with Antlr's generated parser)
      IcssParser parser = new IcssParser(tokens);
      parser.removeErrorListeners();
      parser.addErrorListener(this);

      ParseTree parseTree = parser.stylesheet();
      AstParser astParser = AstParser.build();
      this.ast = astParser.buildAst(parseTree);

    } catch (RecognitionException e) {
      this.ast = new Ast();
      errors.add(e.getMessage());
    } catch (ParseCancellationException e) {
      this.ast = new Ast();
      errors.add("Syntax error");
    }
    parsed = errors.isEmpty();
    checked = transformed = false;
  }

  public boolean check() {
    if (ast == null) {
      return false;
    }

    (new Checker()).check(this.ast);

    List<SemanticError> errors = this.ast.getErrors();
    if (!errors.isEmpty()) {
      for (SemanticError e : errors) {
        this.errors.add(e.toString());
      }
    }

    checked = errors.isEmpty();
    transformed = false;
    return errors.isEmpty();
  }

  public void clearErrors() {
    errors.clear();
  }

  public void transform() {
    if (ast == null) {
      return;
    }

    (new Evaluator()).apply(ast);


    transformed = errors.isEmpty();
  }

  public String generate() {
    Generator generator = new Generator();
    return generator.generate(ast);
  }

  //Catch ANTLR errors
  @Override
  public void reportAmbiguity(Parser arg0, DFA arg1, int arg2, int arg3, boolean arg4, BitSet arg5, ATNConfigSet arg6) {
  }

  @Override
  public void reportAttemptingFullContext(Parser arg0, DFA arg1, int arg2, int arg3, BitSet arg4, ATNConfigSet arg5) {
  }

  @Override
  public void reportContextSensitivity(Parser arg0, DFA arg1, int arg2, int arg3, int arg4, ATNConfigSet arg5) {
  }

  @Override
  public void syntaxError(
      Recognizer<?, ?> arg0,
      Object arg1,
      int arg2,
      int arg3,
      String arg4,
      RecognitionException arg5
  ) {
    errors.add("Syntax error: " + arg4);
  }
}
