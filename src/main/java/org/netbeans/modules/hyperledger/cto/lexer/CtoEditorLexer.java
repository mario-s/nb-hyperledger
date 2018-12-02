package org.netbeans.modules.hyperledger.cto.lexer;

import java.util.Map;
import java.util.function.Function;
import org.antlr.v4.runtime.CharStream;
import org.netbeans.api.lexer.Token;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 */
public final class CtoEditorLexer implements Lexer<CtoTokenId> {

    private final static String NAME = "CtoEditor";

    private final LexerRestartInfo<CtoTokenId> info;
    private final Map<Integer, CtoTokenId> idToToken;
    
    private final Function<CtoTokenId, Token<CtoTokenId>> tokenFactory;
    private final CtoLexer ctoLexer;

    public CtoEditorLexer(LexerRestartInfo<CtoTokenId> info, Map<Integer, CtoTokenId> idToToken) {
        this.info = info;
        this.idToToken = idToToken;
        this.tokenFactory = id -> info.tokenFactory().createToken(id);
        
        CharStream stream = new CtoCharStream(info.input(), NAME);
        ctoLexer = new CtoLexer(stream);
    }

    @Override
    public Token<CtoTokenId> nextToken() {
        org.antlr.v4.runtime.Token token = ctoLexer.nextToken();

        Token<CtoTokenId> createdToken = null;

        int type = token.getType();
        if (type != -1) {
            createdToken = createToken(type);
        } else if (info.input().readLength() > 0) {
            createdToken = createToken(CtoLexer.WS);
        }

        return createdToken;
    }

    private Token<CtoTokenId> createToken(int type) {
        Function<Integer, CtoTokenId> mapping = idToToken::get;
        return mapping.andThen(tokenFactory).apply(type);
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
