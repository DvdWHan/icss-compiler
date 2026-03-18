set grammar_file=src/main/antlr4/nl/han/ica/icss/parser/Icss.g4
set class=stylesheet

java -cp "%ANTLR4_JAR%" org.antlr.v4.gui.Interpreter %grammar_file% %class% -gui
