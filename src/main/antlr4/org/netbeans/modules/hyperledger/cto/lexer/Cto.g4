grammar Cto;

sourceUnit: namespaceDeclaration? importDeclaration* typeDeclaration*
    | EOF;

namespaceDeclaration: NAMESPACE qualifiedName;

importDeclaration: IMPORT qualifiedName ('.' '*')? ;

typeDeclaration: (assetDeclaration | participantDeclaration | transactionDeclaration | eventDeclaration);

assetDeclaration: CLASS_ASSET qualifiedName IDENTIFIED qualifiedName classBody;

participantDeclaration: CLASS_PARTICIPANT qualifiedName IDENTIFIED qualifiedName classBody;

transactionDeclaration: CLASS_TRANSACTION qualifiedName classBody;

eventDeclaration: CLASS_EVENT qualifiedName classBody;

qualifiedName: IDENTIFIER ('.' IDENTIFIER)* ;

classBody: '{' classBodyDeclaration* '}';

classBodyDeclaration
    : ' '
    | fieldDeclaration;

fieldDeclaration
    : fieldType IDENTIFIER
    | refType IDENTIFIER;

fieldType: VAR primitiveType ('[' ']')*;

refType: REF IDENTIFIER ('[' ']')*;

primitiveType
    : BOOLEAN
    | DATE_TIME
    | DOUBLE
    | INTEGER
    | STRING;


ABSTRACT
    : 'abstract';
BOOLEAN
    : 'Boolean';
CLASS_ASSET
    : 'asset';
CLASS_PARTICIPANT
    : 'participant';
CLASS_TRANSACTION
    : 'transaction';
CLASS_EVENT
    : 'event';
CONCEPT
    : 'concept';
DATE_TIME
    : 'DateTime';
DOUBLE
    : 'Double';
ENUM
    : 'enum';
EXTENDS
    : 'extends';
IDENTIFIED
    : 'identified by';
IDENTIFIER
    : Letter LetterOrDigit*;
IMPORT
    : 'import';
INTEGER
    : 'integer';
NAMESPACE
    : 'namespace';
STRING
    : 'String';
REF
    : '-->';
VAR
    : 'o';
COMMENT
    : '/*' .*? '*/' -> channel(HIDDEN) ;
LINE_COMMENT
    : '//' ~[\r\n]* -> channel(HIDDEN) ;
WS
    : [ \t\n\r]+ -> skip ;

fragment LetterOrDigit
    : Letter
    | [0-9];

fragment Letter
    : [a-zA-Z$_]
    | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
    ;

