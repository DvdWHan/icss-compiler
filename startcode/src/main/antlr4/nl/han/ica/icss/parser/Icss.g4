grammar Icss;

WHITESPACE: [ \t\r\n]+ -> skip;

OPENING_BRACKET: '[';
CLOSING_BRACKET: ']';
OPENING_BRACE: '{';
CLOSING_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MINUS: '-';
ASTERISK: '*';
HASHTAG: '#';
PERIOD: '.';
COLON_EQUALS: ':='; // considered one token
EQUALS: '=';

IF: 'if';
ELSE: 'else';

TRUE: 'TRUE';
FALSE: 'FALSE';
PIXEL_SIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;
COLOR: '#' [0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f]; // colors must be six hexadecimal characters long

// elements, classes, and ids must be snake-case
SNAKE_CASE_IDENTIFIER: [a-z][a-z0-9\-]*;
// variables must be PascalCase
PASCAL_CASE_IDENTIFIER: [A-Z][A-Za-z0-9_]*;

// grammar rule names inspired by https://www.w3.org/TR/CSS2/grammar.html#grammar
stylesheet: variableAssignment* ruleset* EOF;

// variable assignments can only occur at the top of the stylesheet or individual rulesets
variableAssignment: variableIdentifier COLON_EQUALS expression SEMICOLON;

variableIdentifier: PASCAL_CASE_IDENTIFIER;

expression: booleanLiteral | colorLiteral | mathExpression;
booleanLiteral: FALSE | TRUE;
colorLiteral: COLOR;

// this is known as "recursive descent parsing"
mathExpression: additionExpression;
additionExpression: multiplicationExpression ((PLUS | MINUS) multiplicationExpression)*;
multiplicationExpression: unaryExpression (ASTERISK unaryExpression)*;
unaryExpression: (PLUS | MINUS) unaryExpression | primaryExpression;
primaryExpression: numericLiteral | variableIdentifier;
numericLiteral: scalarLiteral | pixelLiteral | percentageLiteral;
scalarLiteral: SCALAR;
pixelLiteral: PIXEL_SIZE;
percentageLiteral: PERCENTAGE;

ruleset: selector OPENING_BRACE body CLOSING_BRACE;

selector: elementSelector | idSelector | classSelector;
elementSelector: SNAKE_CASE_IDENTIFIER;
idSelector: HASHTAG SNAKE_CASE_IDENTIFIER;
classSelector: PERIOD SNAKE_CASE_IDENTIFIER;

body: (variableAssignment | declaration | conditionalStatement)*;

declaration: property COLON expression SEMICOLON;
property: 'color' | 'background-color' | 'width' | 'height'; // only these properties are allowed

conditionalStatement: IF OPENING_BRACKET expression CLOSING_BRACKET
    OPENING_BRACE body CLOSING_BRACE
    (ELSE OPENING_BRACE body CLOSING_BRACE)?;
