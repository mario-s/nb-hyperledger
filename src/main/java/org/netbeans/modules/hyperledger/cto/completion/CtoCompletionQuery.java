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

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.Optional.*;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;

/**
 *
 */
final class CtoCompletionQuery extends AsyncCompletionQuery {

    private static final String ICON_PATH = "org/netbeans/modules/hyperledger/cto/%s";

    private final Function<TokenTaxonomy.Category, List<CtoTokenId>> tokenProvider;

    CtoCompletionQuery() {
        tokenProvider = category -> TokenTaxonomy.getDefault().tokens(category);
    }

    @Override
    protected void query(CompletionResultSet crs, Document dcmnt, int offset) {
        crs.addAllItems(getKeywordItems(offset));
        crs.addAllItems(getPrimitiveTypeItems(offset));
        crs.finish();
    }

    private List<? extends AbstractCompletionItem> getKeywordItems(int offset) {
        Function<CtoTokenId, KeywordCompletionItem> mapping = token -> {
            Optional<String> iconPath = iconPath(token.ordinal());
            return new KeywordCompletionItem(iconPath, token.name(), offset);
        };
        List<CtoTokenId> tokens = tokenProvider.apply(TokenTaxonomy.Category.keyword);
        return map(tokens, mapping);
    }

    private List<? extends AbstractCompletionItem> getPrimitiveTypeItems(int offset) {
        Function<CtoTokenId, PrimitiveTypeCompletionItem> mapping = token -> {
            return new PrimitiveTypeCompletionItem(token.name(), offset);
        };
        List<CtoTokenId> tokens = tokenProvider.apply(TokenTaxonomy.Category.type);
        return map(tokens, mapping);
    }

    private List<? extends AbstractCompletionItem> map(List<CtoTokenId> tokens, Function<CtoTokenId, ? extends AbstractCompletionItem> mapping) {
        return tokens.stream().map(t -> mapping.apply(t)).collect(toList());
    }


    private Optional<String> iconPath(int type) {
        switch (type) {
            case CtoLexer.ASSET:
                return of(format(ICON_PATH, "asset.png"));
            case CtoLexer.PARTICIPANT:
                return of(format(ICON_PATH, "participant.png"));
            case CtoLexer.TRANSACTION:
                return of(format(ICON_PATH, "transaction.png"));
            default:
                return empty();
        }
    }

}
