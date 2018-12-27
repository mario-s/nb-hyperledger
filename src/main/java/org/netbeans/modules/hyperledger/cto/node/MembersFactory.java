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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import static java.lang.String.format;
import java.util.List;
import java.util.Optional;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;
import org.netbeans.modules.hyperledger.cto.grammar.CtoParser;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Pair;

/**
 *
 * @author mario.schroeder
 */
final class MembersFactory extends ChildFactory<Pair<String,String>> implements PropertyChangeListener{
    private static final String MEMBER = "%s : %s";
    
    @StaticResource
    private static final String ICON = "org/netbeans/modules/hyperledger/cto/blue.png";
    
    private final DataNode root;
    
    public MembersFactory(DataNode root) {
        this.root = root;
        root.getDataObject().addPropertyChangeListener(this);
    }
    
    @Override
    protected Node createNodeForKey(Pair<String,String> pair) {
        AbstractNode node = new AbstractNode(Children.LEAF);
        node.setDisplayName(format(MEMBER, pair.first(), pair.second()));
        node.setIconBaseWithExtension(ICON);
        return node;
    }

    @Override
    protected boolean createKeys(List<Pair<String,String>> toPopulate) {
        
        ParserListener listener = new ParserListener();
        FileObject fileObject = root.getDataObject().getPrimaryFile();

        try {
            CharStream input = new FileCharStream(fileObject.getPath());
            Lexer lexer = new CtoLexer(input);
            TokenStream tokenStream = new CommonTokenStream(lexer);
            CtoParser parser = new CtoParser(tokenStream);
            parser.addParseListener(listener);
            parser.modelUnit();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        updateRootName(listener.getNamespace());
        listener.getMembers().forEach((k,v) -> toPopulate.add(Pair.of(k, v)));

        return true;
    }

    private void updateRootName(Optional<String> namespace) {
        String oldName = root.getDisplayName();
        String rootName = namespace.orElse(oldName);
        if(!rootName.equals(oldName)) {
            root.setDisplayName(rootName);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        refresh(true);
    }
}
