package org.netbeans.modules.hyperledger.cto.lexer;

import java.util.function.Function;
import org.antlr.v4.runtime.CharStream;
import org.netbeans.api.lexer.Token;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;
/**
 *
 */
public final class CtoEditorLexer implements Lexer<CtoTokenId>{
    private final static String NAME = "CtoEditor";
    
    private final LexerRestartInfo<CtoTokenId> info;
    private final CtoLexer ctoLexer;
    private final CtoLanguageHierarchy hierachy;
    
    private final Function<Integer, CtoTokenId> ctoTokenFactory;
    private final Function<CtoTokenId, Token<CtoTokenId>> tokenFactory;

    public CtoEditorLexer(LexerRestartInfo<CtoTokenId> info, CtoLanguageHierarchy hierachy) {
        this.info = info;
        CharStream stream = new AntlrCharStream(info.input(), NAME);
        ctoLexer = new CtoLexer(stream);
        this.hierachy = hierachy;
        
        this.ctoTokenFactory = type -> hierachy.getToken(type);
        this.tokenFactory = tokenId -> info.tokenFactory().createToken(tokenId);
    }
    
    @Override
    public Token<CtoTokenId> nextToken() {
        org.antlr.v4.runtime.Token token = ctoLexer.nextToken();                

        Token<CtoTokenId> createdToken = null;

        if (token.getType() != -1){
            int type = token.getType();
            CtoTokenId tokenId = ctoTokenFactory.apply(type);
            if(tokenId != null) {
                createdToken = tokenFactory.apply(tokenId);
            } else {
                createdToken = ctoTokenFactory.andThen(tokenFactory).apply(CtoLexer.COLON);
            }
        }  else if(info.input().readLength() > 0){
            createdToken = ctoTokenFactory.andThen(tokenFactory).apply(CtoLexer.WS);
        }

        return createdToken;
    }

    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
        //nothing todo
    }
    
}
