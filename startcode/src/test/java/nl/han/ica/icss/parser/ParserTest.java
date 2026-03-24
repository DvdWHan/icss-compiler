package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.Ast;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParserTest {
  Ast parseTestFile(String resource) throws IOException {
    ClassLoader classLoader = this.getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(resource);
    assert inputStream != null;
    CharStream charStream = CharStreams.fromStream(inputStream);
    IcssLexer lexer = new IcssLexer(charStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    IcssParser parser = new IcssParser(tokens);
    parser.setErrorHandler(new BailErrorStrategy());

    BaseErrorListener errorListener = new BaseErrorListener() {
      private String message;

      public void syntaxError(
          Recognizer<?, ?> recognizer,
          Object offendingSymbol,
          int line,
          int charPositionInLine,
          String msg,
          RecognitionException e
      ) {
        message = msg;
      }

      @Override
      public String toString() {
        return message;
      }
    };
    parser.removeErrorListeners();
    parser.addErrorListener(errorListener);
    ParseTree parseTree = parser.stylesheet();
    AstParser astParser = AstParser.build();
    Ast ast = new Ast();
    try {
      ast = astParser.buildAst(parseTree);
    } catch (ParseCancellationException exception) {
      fail(errorListener.toString());
    }
    return ast;
  }

  @Test
  void testParseLevel0() throws IOException {
    Ast sut = parseTestFile("level0.icss");
    Ast exp = Fixtures.uncheckedLevel0();
    assertEquals(exp, sut);
  }

  @Test
  void testParseLevel1() throws IOException {
    Ast sut = parseTestFile("level1.icss");
    Ast exp = Fixtures.uncheckedLevel1();
    assertEquals(exp, sut);
  }

  @Test
  void testParseLevel2() throws IOException {
    Ast sut = parseTestFile("level2.icss");
    Ast exp = Fixtures.uncheckedLevel2();
    assertEquals(exp, sut);
  }

  @Test
  void testParseLevel3() throws IOException {
    Ast sut = parseTestFile("level3.icss");
    Ast exp = Fixtures.uncheckedLevel3();
    assertEquals(exp, sut);
  }

  @Test
  void testParseLevel4() throws IOException {
    Ast sut = parseTestFile("level4.icss");
    Ast exp = Fixtures.uncheckedLevel4();
    assertEquals(exp, sut);
  }
}
