grammar Cto;

sourceUnit
    : namespaceDeclaration?
    | EOF;

namespaceDeclaration
    : NAMESPACE WSPC+ IDENTIFIER;

assetDeclaration
    : CLASS_ASSET IDENTIFIER IDENTIFIED IDENTIFIER;

IDENTIFIER
    :   [A-Za-z0-9]+;

WSPC
    : ~[\\s];

WORD
    : ~[\\w];
NAMESPACE
    : 'namespace';
CLASS_ASSET
    : 'asset';
CLASS_PARTICIPANT
    : 'participant';
CLASS_TRANSACTION
    : 'transaction';
CLASS_EVENT
    : 'event';
IDENTIFIED
    : 'identified by';
VAR
    : 'o ';
REF
    : '-->';
COMMENT
    : '/*' .*? '*/' -> channel(HIDDEN) ;
LINE_COMMENT
    : '//' ~[\r\n]* -> channel(HIDDEN) ;
WS 
    : [ \t\n\r]+ -> skip ;



