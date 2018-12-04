package org.netbeans.modules.hyperledger.cto.completion;

import javax.swing.text.Document;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.support.AsyncCompletionQuery;

/**
 *
 */
final class CtoCompletionQuery extends AsyncCompletionQuery{

    @Override
    protected void query(CompletionResultSet crs, Document dcmnt, int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
