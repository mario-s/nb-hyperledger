package org.netbeans.modules.hyperledger.cto.lexer;

import org.antlr.v4.runtime.CharStream;
import org.netbeans.api.lexer.Token;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;
/**
 *
 */
public final class CtoEditorLexer implements Lexer<CtoTokenId>{
    
    private final LexerRestartInfo<CtoTokenId> info;
    private final CtoLexer ctoLexer;
    private final CtoLanguageHierarchy hierachy;

    public CtoEditorLexer(LexerRestartInfo<CtoTokenId> info, CtoLanguageHierarchy hierachy) {
        this.info = info;
        CharStream stream = null; //TODO
        ctoLexer = new CtoLexer(stream);
        this.hierachy = hierachy;
    }
    
    @Override
    public Token<CtoTokenId> nextToken() {
        org.antlr.v4.runtime.Token token = ctoLexer.nextToken();                

        Token<CtoTokenId> createdToken = null;

        if (token.getType() != -1){
            int type = token.getType();
            createdToken = createToken(type);
        }  else if(info.input().readLength() > 0){
            createdToken = createToken(CtoLexer.WS);
        }

        return createdToken;
    }
    
    private Token<CtoTokenId> createToken(int type) {
        CtoTokenId tokenId  = hierachy.getToken(type);
        return info.tokenFactory().createToken(tokenId);
    }

    @Override
    public Object state() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void release() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
