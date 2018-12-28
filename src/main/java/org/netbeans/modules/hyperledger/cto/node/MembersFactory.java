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

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.hyperledger.cto.grammar.CtoParser;
import org.netbeans.modules.hyperledger.cto.parser.CtoParserFactory;
import org.netbeans.modules.hyperledger.cto.parser.ParserListener;
import org.openide.cookies.EditorCookie;
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
import org.openide.util.Pair;

/**
 *
 * @author mario.schroeder
 */
final class MembersFactory extends ChildFactory<Pair<String, String>> implements DocumentListener {

    private static final String MEMBER = "%s : %s";

    @StaticResource
    private static final String ICON = "org/netbeans/modules/hyperledger/cto/blue.png";

    private final DataNode root;

    private Document document;

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
    protected Node createNodeForKey(Pair<String, String> pair) {
        AbstractNode node = new AbstractNode(Children.LEAF);
        node.setDisplayName(format(MEMBER, pair.first(), pair.second()));
        node.setIconBaseWithExtension(ICON);
        return node;
    }

    @Override
    protected boolean createKeys(List<Pair<String, String>> toPopulate) {

        ParserListener listener = new ParserListener();

        try {
            String text = getText();
            CtoParser parser = CtoParserFactory.getParserProvider().apply(text);
            parser.addParseListener(listener);
            parser.modelUnit();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        updateRootName(listener.getNamespace());
        listener.getMembers().forEach((k, v) -> toPopulate.add(Pair.of(k, v)));

        return true;
    }

    private String getText() throws IOException {
        if (document != null) {
            try {
                int len = document.getLength();
                return document.getText(0, len);
            } catch (BadLocationException ex) {
                return getTextFromFile();
            } finally {
                document = null;
            }
        }
        return getTextFromFile();
    }

    private String getTextFromFile() throws IOException {
        return getPrimaryFile().asText();
    }

    private void updateRootName(Optional<String> namespace) {
        String oldName = root.getDisplayName();
        String rootName = namespace.orElse(oldName);
        if (!rootName.equals(oldName)) {
            root.setDisplayName(rootName);
        }
    }

    private Optional<Document> findDocument() {
        DataObject dataObject = getDataObject();
        EditorCookie ec = dataObject.getLookup().lookup(EditorCookie.class);
        if (ec != null) {
            JEditorPane[] panes = ec.getOpenedPanes();
            if (hasPanes(panes)) {
                return getDocument(panes);
            } else {
                ec.open();
                try {
                    ec.openDocument();
                    panes = ec.getOpenedPanes();
                    if (hasPanes(panes)) {
                        return getDocument(panes);
                    }
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        return empty();
    }

    private boolean hasPanes(JEditorPane[] panes) {
        return panes != null && panes.length > 0;
    }
    
    private Optional<Document> getDocument(JEditorPane[] panes) {
        return of(panes[0].getDocument());
    }

    void register() {
        getPrimaryFile().addFileChangeListener(adapter);
        SwingUtilities.invokeLater(() -> findDocument().ifPresent(doc -> {
            refresh(doc); //force a refresh of the tree
            doc.addDocumentListener(this);
        }));
    }

    void cleanup() {
        getPrimaryFile().removeFileChangeListener(adapter);
        SwingUtilities.invokeLater(() -> findDocument().ifPresent(doc -> doc.removeDocumentListener(this)));
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        refresh(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        refresh(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        refresh(e);
    }

    private void refresh(DocumentEvent e) {
        refresh(e.getDocument());
    }

    private void refresh(Document doc) {
        this.document = doc;
        refresh(false);
    }
}
