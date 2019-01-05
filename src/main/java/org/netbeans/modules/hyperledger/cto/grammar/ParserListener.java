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
package org.netbeans.modules.hyperledger.cto.grammar;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author mario.schroeder
 */
public final class ParserListener extends CtoParserBaseListener {

    private final Map<String, Integer> members = new TreeMap<>();

    public Map<String, Integer> getMembers() {
        return members;
    }

    private void addNode(String text, int id) {
        members.put(text, id);
    }

    private void addNode(TerminalNode node, int id) {
        if (node != null && !(node instanceof ErrorNode)) {
            addNode(node.getText(), id);
        }
    }

    @Override
    public void exitNamespaceDeclaration(CtoParser.NamespaceDeclarationContext ctx) {
        CtoParser.QualifiedNameContext qualCtx = ctx.qualifiedName();
        if (qualCtx != null) {
            List<TerminalNode> identifiers = qualCtx.IDENTIFIER();
            String name = identifiers.stream().map(n -> n.getText()).collect(Collectors.joining("."));
            addNode(name, CtoLexer.NAMESPACE);
        }
    }

    @Override
    public void exitAssetDeclaration(CtoParser.AssetDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.ASSET);
    }

    @Override
    public void exitParticipantDeclaration(CtoParser.ParticipantDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.PARTICIPANT);
    }

    @Override
    public void exitTransactionDeclaration(CtoParser.TransactionDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.TRANSACTION);
    }

    @Override
    public void exitEventDeclaration(CtoParser.EventDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.EVENT);
    }

    @Override
    public void exitEnumDeclaration(CtoParser.EnumDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.ENUM);
    }

    @Override
    public void exitConceptDeclaration(CtoParser.ConceptDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(0), CtoLexer.CONCEPT);
    }
}
