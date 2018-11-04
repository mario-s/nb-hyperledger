lexer grammar CtoLexer;

ABSTRACT: 'abstract';
BOOLEAN: 'Boolean';
CLASS_ASSET: 'asset';
CLASS_PARTICIPANT: 'participant';
CLASS_TRANSACTION: 'transaction';
CLASS_EVENT: 'event';
CONCEPT: 'concept';
DATE_TIME: 'DateTime';
DOUBLE: 'Double';
ENUM: 'enum';
EXTENDS: 'extends';
IDENTIFIED: 'identified by';
IDENTIFIER: Letter LetterOrDigit*;
IMPORT: 'import';
INTEGER: 'Integer';
PACKAGE: 'namespace';
STRING: 'String';
REF: '-->';
VAR: 'o';

LPAREN:             '(';
RPAREN:             ')';
LBRACE:             '{';
RBRACE:             '}';
LBRACK:             '[';
RBRACK:             ']';
SEMI:               ';';
COMMA:              ',';
DOT:                '.';
MUL:                '*';

COMMENT: '/*' .*? '*/' -> channel(HIDDEN) ;
LINE_COMMENT: '//' ~[\r\n]* -> channel(HIDDEN) ;
WS: [ \t\n\r]+ -> skip ;

fragment LetterOrDigit
    : Letter
    | [0-9];

fragment Letter
    : [a-zA-Z$_]
    | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
    ;