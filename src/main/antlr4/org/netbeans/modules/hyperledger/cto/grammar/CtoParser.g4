parser grammar CtoParser;

options { tokenVocab=CtoLexer; }

modelUnit
    : namespaceDeclaration? importDeclaration* typeDeclaration* EOF
    ;

namespaceDeclaration
    : NAMESPACE qualifiedName
    ;

importDeclaration
    : IMPORT qualifiedName (DOT '*')?
    ;

typeDeclaration
    : (assetDeclaration 
      | enumDeclaration 
      | participantDeclaration
      | transactionDeclaration
      | eventDeclaration
      | annotationTypeDeclaration)
    ;

modifier
    : classOrInterfaceModifier;

classOrInterfaceModifier
    : annotation
    | ABSTRACT
    ;

variableModifier
    : annotation
    ;

assetDeclaration
    : classOrInterfaceModifier*
      ASSET IDENTIFIER
      (EXTENDS typeType)?
      IDENTIFIED
      IDENTIFIER
      classBody
    ;

enumDeclaration
    : ENUM IDENTIFIER LBRACE enumConstant* RBRACE
    ;

enumConstant
    : VAR IDENTIFIER
    ;

eventDeclaration
    : EVENT IDENTIFIER
      classBody
    ;

participantDeclaration
    : classOrInterfaceModifier*
      PARTICIPANT IDENTIFIER
      (EXTENDS typeType)?
      IDENTIFIED
      IDENTIFIER
      classBody
    ;

transactionDeclaration
    : classOrInterfaceModifier*
      TRANSACTION IDENTIFIER
      classBody
    ;

classBody
    : LBRACE classBodyDeclaration* RBRACE
    ;

classBodyDeclaration
    : ';'
    | fieldDeclaration
    ;

fieldDeclaration
    : fieldType IDENTIFIER 
    | refType identifier;

fieldType
    : VAR (primitiveType | IDENTIFIER) (LBRACK RBRACK)*;

refType
    : REF IDENTIFIER (LBRACK RBRACK)*;

identifier: IDENTIFIER | ASSET;

variableDeclaratorId
    : IDENTIFIER ('[' ']')*
    ;

qualifiedNameList
    : qualifiedName (',' qualifiedName)*
    ;

formalParameters
    : '(' formalParameterList? ')'
    ;

formalParameterList
    : formalParameter (',' formalParameter)* (',' lastFormalParameter)?
    | lastFormalParameter
    ;

formalParameter
    : variableModifier* typeType variableDeclaratorId
    ;

lastFormalParameter
    : variableModifier* typeType ELLIPSIS variableDeclaratorId
    ;

qualifiedName
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

literal
    : integerLiteral
    | floatLiteral
    | CHAR_LITERAL
    | STRING_LITERAL
    | BOOL_LITERAL
    | NULL_LITERAL
    ;

integerLiteral
    : DECIMAL_LITERAL
    | HEX_LITERAL
    | OCT_LITERAL
    | BINARY_LITERAL
    ;

floatLiteral
    : FLOAT_LITERAL
    | HEX_FLOAT_LITERAL
    ;

// ANNOTATIONS

annotation
    : '@' qualifiedName ('(' ( elementValuePairs | elementValue )? ')')?
    ;

elementValuePairs
    : elementValuePair (',' elementValuePair)*
    ;

elementValuePair
    : IDENTIFIER '=' elementValue
    ;

elementValue
    : expression
    | annotation
    | elementValueArrayInitializer
    ;

elementValueArrayInitializer
    : '{' (elementValue (',' elementValue)*)? (',')? '}'
    ;

annotationTypeDeclaration
    : '@' IDENTIFIER annotationTypeBody
    ;

annotationTypeBody
    : '{' (annotationTypeElementDeclaration)* '}'
    ;

annotationTypeElementDeclaration
    : modifier* annotationTypeElementRest
    | ';' // this is not allowed by the grammar, but apparently allowed by the actual compiler
    ;

annotationTypeElementRest
    : typeType annotationMethodOrConstantRest ';'
    | enumDeclaration ';'?
    | annotationTypeDeclaration ';'?
    ;

annotationMethodOrConstantRest
    : annotationMethodRest
    ;

annotationMethodRest
    : IDENTIFIER '(' ')' defaultValue?
    ;

defaultValue
    : DEFAULT elementValue
    ;

// EXPRESSIONS

parExpression
    : '(' expression ')'
    ;

expression
    : primary
    | expression '[' expression ']'
    | '(' typeType ')' expression
    ;


primary
    : '(' expression ')'
    | literal
    | IDENTIFIER
    ;


typeType
    : annotation? (primitiveType) ('[' ']')*
    ;

primitiveType
    : BOOLEAN
    | DATE_TIME
    | DOUBLE
    | INTEGER
    | LONG
    | STRING
    ;

