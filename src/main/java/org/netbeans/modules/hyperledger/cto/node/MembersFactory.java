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

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import static java.lang.String.format;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.hyperledger.LookupContext;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;
import org.netbeans.modules.hyperledger.cto.grammar.CtoParser;
import org.netbeans.modules.hyperledger.cto.grammar.CtoVocabulary;
import org.netbeans.modules.hyperledger.cto.grammar.ParserListener;
import org.netbeans.modules.hyperledger.cto.grammar.ParserProvider;
import org.openide.filesystems.FileChangeAdapter;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;


/**
 *
 * @author mario.schroeder
 */
final class MembersFactory extends ChildFactory<Entry<String, Integer>> implements LookupListener {

    private static final String MEMBER = "%s : %s";

    @StaticResource
    private static final String ICON = "org/netbeans/modules/hyperledger/cto/blue.png";

    private static final CtoVocabulary VOCABULARY = new CtoVocabulary();

    private final DataNode root;

    private final LookupContext lookupContext = LookupContext.INSTANCE;

    private Lookup.Result<Map> selection;

    private Map<String, Integer> members = new HashMap();

    private final FileChangeAdapter adapter = new FileChangeAdapter() {
        @Override
        public void fileChanged(FileEvent fe) {
            refresh(false);
        }
    };

    MembersFactory(DataNode root) {
        this.root = root;
    }

    private FileObject getPrimaryFile() {
        return getDataObject().getPrimaryFile();
    }

    private DataObject getDataObject() {
        return root.getDataObject();
    }

    @Override
    protected Node createNodeForKey(Entry<String, Integer> entry) {
        if (CtoLexer.NAMESPACE == entry.getValue()) {
            updateRootName(entry.getKey());
            return null;
        } else {
            AbstractNode node = new AbstractNode(Children.LEAF);
            String type = VOCABULARY.getDisplayName(entry.getValue());
            node.setDisplayName(format(MEMBER, entry.getKey(), type));
            node.setIconBaseWithExtension(ICON);
            return node;
        }
    }

    @Override
    protected boolean createKeys(List<Entry<String, Integer>> toPopulate) {
        //load text from file, since the members are empty initialy
        if (members.isEmpty()) {
            ParserListener listener = new ParserListener();

            try {
                String text = getPrimaryFile().asText();
                CtoParser parser = ParserProvider.INSTANCE.apply(text);
                parser.addParseListener(listener);
                parser.modelUnit();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }

            members = listener.getMembers();
        }

        members.entrySet().forEach(toPopulate::add);

        return true;
    }

    private void updateRootName(String rootName) {
        String oldName = root.getDisplayName();
        if (!rootName.equals(oldName)) {
            root.setDisplayName(rootName);
        }
    }

    void register() {
        getPrimaryFile().addFileChangeListener(adapter);
        selection = lookupContext.getLookup().lookupResult(Map.class);
        selection.addLookupListener(this);
    }

    void cleanup() {
        getPrimaryFile().removeFileChangeListener(adapter);
        selection.removeLookupListener(this);
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        if (selection != null) {
            //consume and remove
            Collection<? extends Map> results = selection.allInstances();
            if (!results.isEmpty()) {
                Map<String, Integer> result = results.iterator().next();
                members = result;
                lookupContext.remove(result);
                refresh(false);
            }
        }
    }
}
