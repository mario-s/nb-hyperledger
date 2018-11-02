package org.netbeans.modules.hyperledger.cto.lexer;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.modules.hyperledger.cto.FileType;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 */
public class CtoLanguageHierarchy extends LanguageHierarchy<CtoTokenId>{
    
    private final List<CtoTokenId> tokens;
    private final Map<Integer, CtoTokenId> idToToken;
    
    public CtoLanguageHierarchy() {
        idToToken = new HashMap<>();
        
        tokens = Arrays.<CtoTokenId>asList(new CtoTokenId[]{
            //[PENDING]
        });
        tokens.forEach(token -> idToToken.put(token.ordinal(), token));
    } 


    @Override
    protected Collection<CtoTokenId> createTokenIds() {
        return tokens;
    }

    @Override
    protected Lexer<CtoTokenId> createLexer(LexerRestartInfo<CtoTokenId> info) {
        return new MyCtoLexer(info);
    }

    @Override
    protected String mimeType() {
        return FileType.MIME;
    }
    
}
