package org.netbeans.modules.hyperledger.cto.lexer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.antlr.v4.runtime.Vocabulary;
import org.netbeans.modules.hyperledger.cto.FileType;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 */
public class CtoLanguageHierarchy extends LanguageHierarchy<CtoTokenId> {

    enum Category {
        keyword, type, field, separator, value, comment, text
    }

    private final List<CtoTokenId> tokens;
    private final Map<Integer, CtoTokenId> idToToken;

    public CtoLanguageHierarchy() {
        idToToken = new HashMap<>();
        tokens = new ArrayList<>();
        
        createTokenMapping();
    }

    private void createTokenMapping() {
        Vocabulary vocabulary = CtoLexer.VOCABULARY;
        int max = vocabulary.getMaxTokenType()+1;
        for (int i = 1; i < max; i++) {
            CtoTokenId token = new CtoTokenId(vocabulary.getDisplayName(i), getCategory(i), i);
            tokens.add(token);
        }
        tokens.forEach(token -> idToToken.put(token.ordinal(), token));
    }

    private String getCategory(int token) {
        Function<Integer, Category> mapping = t -> {
            if (t < CtoLexer.BOOLEAN) {
                return Category.keyword;
            } else if (t < CtoLexer.LPAREN) {
                return Category.type;
            } else if (t < CtoLexer.REF) {
                return Category.separator;
            } else if (t < CtoLexer.DECIMAL_LITERAL) {
                return Category.field;
            } else if (t < CtoLexer.WS || t == CtoLexer.CHAR_LITERAL || t == CtoLexer.STRING_LITERAL) {
                return Category.value;
            } else if (t == CtoLexer.COMMENT || t == CtoLexer.LINE_COMMENT) {
                return Category.comment;
            }
            return Category.text;
        };

        return mapping.apply(token).name();
    }

    @Override
    protected Collection<CtoTokenId> createTokenIds() {
        return tokens;
    }

    @Override
    protected Lexer<CtoTokenId> createLexer(LexerRestartInfo<CtoTokenId> info) {
        return new CtoEditorLexer(info, idToToken);
    }

    @Override
    protected String mimeType() {
        return FileType.MIME;
    }
}
