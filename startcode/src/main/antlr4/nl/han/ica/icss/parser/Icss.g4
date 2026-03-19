grammar Icss;

IF: 'if';
ELSE: 'else';
OPENING_BRACKET: '[';
CLOSING_BRACKET: ']';

TRUE: 'TRUE';
FALSE: 'FALSE';
PIXEL_SIZE: SCALAR 'px';
PERCENTAGE: SCALAR '%';
SCALAR: [0-9]+;

// color must be six hexadecimal characters long
COLOR: HASHTAG [0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f];

// elements, ids, and classes must be snake-case
SNAKE_CASE_IDENTIFIER: [a-z][a-z0-9\-]*;
PASCAL_CASE_IDENTIFIER: [A-Z][A-Za-z0-9_]*;

WHITESPACE: [ \t\r\n]+ -> skip;

OPENING_BRACE: '{';
CLOSING_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MINUS: '-';
ASTERISK: '*';
HASHTAG: '#';
PERIOD: '.';
ASSIGNMENT_OPERATOR: ':=';

// grammar rule names inspired by https://www.w3.org/TR/CSS2/grammar.html#grammar
stylesheet: ruleset* EOF;
ruleset: selector OPENING_BRACE declarations CLOSING_BRACE;

selector: elementSelector | idSelector | classSelector;
elementSelector: SNAKE_CASE_IDENTIFIER;
idSelector: PERIOD SNAKE_CASE_IDENTIFIER;
classSelector: HASHTAG SNAKE_CASE_IDENTIFIER;

declarations: declaration*;
declaration: property COLON literal SEMICOLON;
// only these properties are allowed
property: 'color' | 'background-color' | 'width' | 'height';
literal: MINUS? (SCALAR | PERCENTAGE | PIXEL_SIZE) | COLOR;
