package org.netbeans.modules.hyperledger.cto.lexer;

import java.util.function.Supplier;
import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import org.netbeans.modules.hyperledger.cto.FileType;
import org.openide.util.NbBundle;
import static org.openide.util.NbBundle.getMessage;

/**
 *
 */
@LanguageRegistration(mimeType = FileType.MIME)
@NbBundle.Messages({
    "text/cto=Hyperledger Composer Modeling Language"
})
public class CtoLanguage extends DefaultLanguageConfig{
    
    private final Supplier<Language<?>> supplier;
    
    public CtoLanguage() {
        this.supplier = () -> new CtoLanguageHierarchy().language();
    }

    @Override
    public Language getLexerLanguage() {
        return supplier.get();
    }

    @Override
    public String getDisplayName() {
        return  getMessage(CtoLanguage.class, "text/cto", null);
    }
    
}
