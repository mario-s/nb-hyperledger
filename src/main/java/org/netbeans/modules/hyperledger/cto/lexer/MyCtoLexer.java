package org.netbeans.modules.hyperledger.cto.lexer;

import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;
/**
 *
 */
public final class MyCtoLexer implements Lexer<CtoTokenId>{
    
    private final LexerRestartInfo<CtoTokenId> info;

    public MyCtoLexer(LexerRestartInfo<CtoTokenId> info) {
        this.info = info;
    }
    
    @Override
    public Token<CtoTokenId> nextToken() {
        if (info.input().readLength() < 1) {
            return null;
        }
        return info.tokenFactory().createToken(null);
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
