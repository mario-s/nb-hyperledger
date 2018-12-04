package org.netbeans.modules.hyperledger.cto.completion;

import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.hyperledger.cto.FileType;
import org.netbeans.spi.editor.completion.CompletionProvider;
import org.netbeans.spi.editor.completion.CompletionTask;

/**
 *
 */
@MimeRegistration(mimeType = FileType.MIME, service = CompletionProvider.class)
public class CtoCompletionProvider implements CompletionProvider{

    @Override
    public CompletionTask createTask(int i, JTextComponent jtc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getAutoQueryTypes(JTextComponent jtc, String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
