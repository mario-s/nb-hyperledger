package org.netbeans.modules.hyperledger.cto.lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        keyword, primitive, field, operator, separator, value, comment
    }

    private final List<CtoTokenId> tokens;
    private final Map<Integer, CtoTokenId> idToToken;

    public CtoLanguageHierarchy() {

        Vocabulary vocabulary = CtoLexer.VOCABULARY;

        idToToken = new HashMap<>();
        tokens = new ArrayList<>();
        int max = vocabulary.getMaxTokenType();
        for (int i = 1; i < max; i++) {
            CtoTokenId token = new CtoTokenId(vocabulary.getDisplayName(i), getCategory(i), i);
            tokens.add(token);
        }
        tokens.forEach(token -> idToToken.put(token.ordinal(), token));
    }

    private String getCategory(int token) {
        if (token < CtoLexer.BOOLEAN) {
            return Category.keyword.name();
        } else if (token < CtoLexer.DECIMAL_LITERAL) {
            return Category.primitive.name();
        } else if (token < CtoLexer.LPAREN) {
            return Category.value.name();
        } else if (token < CtoLexer.ASSIGN) {
            return Category.separator.name();
        } else if (token < CtoLexer.ELLIPSIS) {
            return Category.operator.name();
        } else if (token < CtoLexer.WS) {
            return Category.field.name();
        }
        return Category.comment.name();
    }

    @Override
    protected Collection<CtoTokenId> createTokenIds() {
        return tokens;
    }

    @Override
    protected Lexer<CtoTokenId> createLexer(LexerRestartInfo<CtoTokenId> info) {
        return new CtoEditorLexer(info, this);
    }

    @Override
    protected String mimeType() {
        return FileType.MIME;
    }

    CtoTokenId getToken(int id) {
        return idToToken.get(id);
    }
}
