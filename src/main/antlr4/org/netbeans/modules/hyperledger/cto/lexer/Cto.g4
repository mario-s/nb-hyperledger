grammar Cto;

sourceUnit
    : namespaceDeclaration?
    | EOF;

namespaceDeclaration
    : NAMESPACE WSPC+ NONE_WSPC+;
    

assetDeclaration
    : CLASS_ASSET WORD+ IDENTIFIED WORD+;

WSPC
    : ~[\s];
NONE_WSPC
    : ~[\w.+];
WORD
    : ~[\w];
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



