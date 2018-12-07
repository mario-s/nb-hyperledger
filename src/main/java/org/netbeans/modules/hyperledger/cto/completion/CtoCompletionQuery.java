/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.hyperledger.cto.completion;

import java.util.List;
import javax.swing.text.Document;
import org.netbeans.modules.hyperledger.cto.lexer.CtoTokenId;
import org.netbeans.modules.hyperledger.cto.lexer.TokenTaxonomy;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.support.AsyncCompletionQuery;

import static java.util.stream.Collectors.toList;
/**
 *
 */
final class CtoCompletionQuery extends AsyncCompletionQuery{

    @Override
    protected void query(CompletionResultSet crs, Document dcmnt, int i) {
        crs.addAllItems(getKeywordItems());
        crs.finish();
    }
    
    private List<CtoCompletionItem> getKeywordItems() {
        List<CtoTokenId> tokens = TokenTaxonomy.getDefault().tokens(TokenTaxonomy.Category.keyword);
        return tokens.stream().map(t -> new CtoCompletionItem(t.name())).collect(toList());
    }

}