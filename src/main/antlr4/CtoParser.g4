parser grammar CtoParser;

options { tokenVocab=CtoLexer; }

sourceUnit: namespaceDeclaration importDeclaration* typeDeclaration*
    ;

namespaceDeclaration: (PACKAGE)? qualifiedName;

importDeclaration: IMPORT qualifiedName (DOT MUL)? ;

typeDeclaration: (assetDeclaration | participantDeclaration | transactionDeclaration | eventDeclaration);

assetDeclaration: CLASS_ASSET qualifiedName IDENTIFIED qualifiedName classBody;

participantDeclaration: CLASS_PARTICIPANT qualifiedName IDENTIFIED qualifiedName classBody;

transactionDeclaration: CLASS_TRANSACTION qualifiedName classBody;

eventDeclaration: CLASS_EVENT qualifiedName classBody;

qualifiedName: IDENTIFIER (DOT IDENTIFIER)* ;

classBody: LBRACE classBodyDeclaration* RBRACE;

classBodyDeclaration
    : WS
    | fieldDeclaration;

fieldDeclaration
    : fieldType IDENTIFIER
    | refType IDENTIFIER;

fieldType: VAR primitiveType (LBRACK RBRACK)*;

refType: REF IDENTIFIER (LBRACK RBRACK)*;

primitiveType
    : BOOLEAN
    | DATE_TIME
    | DOUBLE
    | INTEGER
    | STRING;