# Togglable Parser Strategy

I have implemented both an [AstListener](src/main/java/nl/han/ica/icss/parser/AstListener.java) and
an [AstVisitor](src/main/java/nl/han/ica/icss/parser/AstListener.java) using the common
interface [AstParser](src/main/java/nl/han/ica/icss/parser/AstParser.java). The strategy can be switched inside the
`AstParser` by modifying the `AstParser.STRATEGY` property. Both strategies perform the same task: building an 
Abstract Syntax Tree (AST) using ANTLR's generated classes. However, the method they use is different. The primary 
differences are that:
1) the listener needs internal storage to keep track of the processed nodes, while the visitor 
uses return values;
2) the listener processes tokens bottom-up, while the visitor processes top-down.

The differences in structure and flow make the visitor have less boilerplate and less prone to bugs.
