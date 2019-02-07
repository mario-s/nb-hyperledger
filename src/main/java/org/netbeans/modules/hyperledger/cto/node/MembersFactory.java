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
import java.util.HashSet;


import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.netbeans.modules.hyperledger.LookupContext;
import org.netbeans.modules.hyperledger.cto.CtoResource;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;
import org.netbeans.modules.hyperledger.cto.grammar.CtoParser;
import org.netbeans.modules.hyperledger.cto.grammar.ParserListener;
import org.netbeans.modules.hyperledger.cto.grammar.ParserProvider;
import org.openide.filesystems.FileChangeAdapter;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author mario.schroeder
 */
final class MembersFactory extends ChildFactory<CtoResource> implements LookupListener {
    
    private final LookupContext lookupContext = LookupContext.INSTANCE;
    
    private Set<CtoResource> resources = new HashSet<>();

    private final DataNode root;

    private Lookup.Result<TreeSet> selection;

    private final FileChangeAdapter adapter = new FileChangeAdapter() {
        @Override
        public void fileChanged(FileEvent fe) {
            resources.clear();
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
    protected Node createNodeForKey(CtoResource resource) {
        if (CtoLexer.NAMESPACE == resource.getType()) {
            updateRootName(resource.getName());
            return null;
        } else {
            return new ChildNode(getDataObject(), resource);
        }
    }

    @Override
    protected boolean createKeys(List<CtoResource> toPopulate) {
        //load text from file, since the resources are empty initialy
        if (resources.isEmpty()) {
            ParserListener listener = new ParserListener();

            try {
                String text = getPrimaryFile().asText();
                CtoParser parser = ParserProvider.INSTANCE.apply(text);
                parser.addParseListener(listener);
                parser.modelUnit();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }

            resources = listener.getResources();
        }

        resources.forEach(toPopulate::add);

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
        selection = lookupContext.getLookup().lookupResult(TreeSet.class);
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
            Collection<? extends Set> results = selection.allInstances();
            if (!results.isEmpty()) {
                resources = results.iterator().next();
                lookupContext.remove(resources);
                refresh(false);
            }
        }
    }
}
