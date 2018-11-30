package org.netbeans.modules.hyperledger.cto.lexer;

import java.util.function.Supplier;
import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.LanguagePath;
import org.netbeans.api.lexer.Token;
import org.netbeans.modules.hyperledger.cto.FileType;
import org.netbeans.spi.lexer.LanguageEmbedding;
import org.netbeans.spi.lexer.LanguageProvider;

/**
 * Service to deliver the language.
 */
@org.openide.util.lookup.ServiceProvider(service=org.netbeans.spi.lexer.LanguageProvider.class)
public class CtoLanguageProvider extends LanguageProvider{
    
    private final Supplier<Language<?>> supplier;

    public CtoLanguageProvider() {
        this.supplier = () -> new CtoLanguageHierarchy().language();
    }

    @Override
    public Language<?> findLanguage(String mime) {
        return (FileType.MIME.equals(mime)) ? supplier.get() : null;
    }

    @Override
    public LanguageEmbedding<?> findLanguageEmbedding(Token<?> token, LanguagePath lp, InputAttributes ia) {
        return null;
    }
    
}
