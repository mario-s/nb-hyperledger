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
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.netbeans.modules.hyperledger.cto.CtoResource;

/**
 *
 * @author mario.schroeder
 */
public final class ParserListener extends CtoParserBaseListener {

    @Deprecated
    private final Map<String, Integer> members = new TreeMap<>();
    
    private final Set<CtoResource> resources = new TreeSet<>();

    @Deprecated
    public Map<String, Integer> getMembers() {
        return members;
    }

    public Set<CtoResource> getResources() {
        return resources;
    }

    @Deprecated
    private void addNode(TerminalNode node, int id) {
        if (node != null && !(node instanceof ErrorNode)) {
            members.put(node.getText(), id);
        }
    }
    
    private void addNode(TerminalNode node, int type, int line) {
        if (node != null && !(node instanceof ErrorNode)) {
            addNode(node.getText(), type, line);
        }
    }
    
    private void addNode(String text, int type, int line) {
        resources.add(new CtoResource(text, type, line));
    }
    
    private int getLine(ParserRuleContext ctx) {
        return ctx.getStart().getLine();
    }

    @Override
    public void exitNamespaceDeclaration(CtoParser.NamespaceDeclarationContext ctx) {
        CtoParser.QualifiedNameContext qualCtx = ctx.qualifiedName();
        if (qualCtx != null) {
            List<TerminalNode> identifiers = qualCtx.IDENTIFIER();
            String name = identifiers.stream().map(TerminalNode::getText).collect(Collectors.joining("."));
            members.put(name, CtoLexer.NAMESPACE);
            addNode(name, CtoLexer.NAMESPACE, getLine(ctx));
        }
    }

    @Override
    public void exitAssetDeclaration(CtoParser.AssetDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.ASSET);
        addNode(ctx.IDENTIFIER(), CtoLexer.ASSET, getLine(ctx));
    }

    @Override
    public void exitParticipantDeclaration(CtoParser.ParticipantDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.PARTICIPANT);
        addNode(ctx.IDENTIFIER(), CtoLexer.PARTICIPANT, getLine(ctx));
    }

    @Override
    public void exitTransactionDeclaration(CtoParser.TransactionDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.TRANSACTION);
        addNode(ctx.IDENTIFIER(), CtoLexer.TRANSACTION, getLine(ctx));
    }

    @Override
    public void exitEventDeclaration(CtoParser.EventDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.EVENT);
        addNode(ctx.IDENTIFIER(), CtoLexer.EVENT, getLine(ctx));
    }

    @Override
    public void exitEnumDeclaration(CtoParser.EnumDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.ENUM);
        addNode(ctx.IDENTIFIER(), CtoLexer.ENUM, getLine(ctx));
    }

    @Override
    public void exitConceptDeclaration(CtoParser.ConceptDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(0), CtoLexer.CONCEPT);
        addNode(ctx.IDENTIFIER(0), CtoLexer.CONCEPT, getLine(ctx));
    }
}
