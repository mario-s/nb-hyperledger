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
package org.netbeans.modules.hyperledger.cto.node;

import java.util.List;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.modules.hyperledger.cto.lexer.Category;
import org.netbeans.modules.hyperledger.cto.lexer.CtoTokenId;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author mario.schroeder
 */
final class MembersTreeFactory extends ChildFactory<TokenId> {
    
    private final DataObject obj;

    public MembersTreeFactory(DataObject obj) {
        this.obj = obj;
    }

    @Override
    protected Node createNodeForKey(TokenId key) {
        AbstractNode node = new AbstractNode(Children.LEAF);
        node.setDisplayName(key.name());
        return node;
    }
    
    @Override
    protected boolean createKeys(List<TokenId> toPopulate) {
        TokenId token = new CtoTokenId("Asset", Category.keyword.name(), 0);
        toPopulate.add(token);
        return true;
    }
    
}
