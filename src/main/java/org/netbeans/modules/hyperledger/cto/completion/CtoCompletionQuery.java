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
import java.util.Optional;
import java.util.function.Function;
import javax.swing.text.Document;
import org.netbeans.modules.hyperledger.cto.lexer.CtoTokenId;
import org.netbeans.modules.hyperledger.cto.lexer.TokenTaxonomy;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.support.AsyncCompletionQuery;

import static java.util.stream.Collectors.toList;
import static java.util.Optional.*;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;
/**
 *
 */
final class CtoCompletionQuery extends AsyncCompletionQuery{
    
    private final Function<TokenTaxonomy.Category, List<CtoTokenId>> tokenProvider;
    
    CtoCompletionQuery() {
        tokenProvider = category -> TokenTaxonomy.getDefault().tokens(category);
    }

    @Override
    protected void query(CompletionResultSet crs, Document dcmnt, int offset) {
        crs.addAllItems(getKeywordItems(offset));
        crs.finish();
    }
    
    private List<CtoCompletionItem> getKeywordItems(int offset) {
        return map(tokenProvider.apply(TokenTaxonomy.Category.keyword), offset);
    }
    
    private List<CtoCompletionItem> map(List<CtoTokenId> tokens, int offset) {
        return tokens.stream().map(t -> map(t, offset)).collect(toList());
    }
    
    private CtoCompletionItem map(CtoTokenId token, int offset) {
        Optional<String> iconPath = iconPath(token.ordinal());
        return new CtoCompletionItem(iconPath, token.name(), offset);
    }
    
    private Optional<String> iconPath(int type) {
        switch(type) {
            case CtoLexer.ASSET:
                return of("org/netbeans/modules/hyperledger/cto/value_16x16.png");
            default: return empty();
        }
    }

}
