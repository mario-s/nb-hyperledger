grammar Cto;

@header {
package org.netbeans.modules.hyperledger.cto.lexer;

}

WSPC
    : ~[\s];
NONE_WSPC
    : [\S+];
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
VAR
    : 'o ';
REF
    : '-->';


namespace
    : NAMESPACE WSPC 'org.example.basic' 
    | EOF;


