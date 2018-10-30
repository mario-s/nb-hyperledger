package org.netbeans.modules.hyperledger.cto.lexer;

%%

%public
%class CtoLexer
%unicode
%standalone


%{
  String name;
%}

%%

"name " [a-zA-Z]+  { name = yytext().substring(5); }
[Hh] "ello" { System.out.print(yytext()+" "+name+"!"); }