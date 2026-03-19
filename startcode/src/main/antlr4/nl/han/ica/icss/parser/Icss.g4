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

// elements, ids, and classes must be snake-case
SNAKE_CASE_IDENTIFIER: [a-z][a-z0-9\-]*;
PASCAL_CASE_IDENTIFIER: [A-Z][A-Za-z0-9_]*;

// grammar rule names inspired by https://www.w3.org/TR/CSS2/grammar.html#grammar
stylesheet: variableAssignment* ruleset* EOF;

// variable assignment can only occur at the top of the stylesheet or individual rulesets
variableAssignment: variableIdentifier COLON_EQUALS expression SEMICOLON;
variableIdentifier: PASCAL_CASE_IDENTIFIER;

expression: literal | variableIdentifier;
literal: FALSE | TRUE | MINUS? (SCALAR | PERCENTAGE | PIXEL_SIZE) | COLOR;

ruleset: selector OPENING_BRACE variableAssignment* declaration* CLOSING_BRACE;

selector: elementSelector | idSelector | classSelector;
elementSelector: SNAKE_CASE_IDENTIFIER;
idSelector: HASHTAG SNAKE_CASE_IDENTIFIER;
classSelector: PERIOD SNAKE_CASE_IDENTIFIER;

declaration: property COLON expression SEMICOLON;
property: 'color' | 'background-color' | 'width' | 'height'; // only these properties are allowed
