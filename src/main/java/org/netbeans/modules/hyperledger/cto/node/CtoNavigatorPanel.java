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

import java.util.Collection;
import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import org.netbeans.modules.hyperledger.cto.FileType;
import org.netbeans.spi.navigator.NavigatorPanel;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.loaders.DataObject;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 *
 * @author mario.schroeder
 */
@NbBundle.Messages({
    "LBL_CtoLang=Composer Model"
})
@NavigatorPanel.Registration(mimeType = FileType.MIME, position = 500, displayName = "#LBL_CtoLang")
public class CtoNavigatorPanel implements NavigatorPanel {

    private static final RequestProcessor RP = new RequestProcessor(CtoNavigatorPanel.class.getName(), 1);

    private Lookup.Result<DataObject> selection;

    private Optional<RootNode> rootNode = empty();

    private final ExplorerManager manager = new ExplorerManager();
    private final Lookup lookup = ExplorerUtils.createLookup(manager, new ActionMap());
    private final JComponent view = new MembersView(manager);

    private final LookupListener selectionListener = ev -> {
        RP.post(() -> {
            if (selection != null) {
                display(selection.allInstances());
            }
        });
    };

    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(CtoNavigatorPanel.class, "LBL_CtoLang");
    }

    @Override
    public String getDisplayHint() {
        return "TODO";
    }

    @Override
    public JComponent getComponent() {
        return view;
    }

    @Override
    public void panelActivated(Lookup lkp) {
        selection = lkp.lookupResult(DataObject.class);
        selection.addLookupListener(selectionListener);
        selectionListener.resultChanged(null);
    }

    @Override
    public void panelDeactivated() {
        selection.removeLookupListener(selectionListener);
        selection = null;
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    private void display(Collection<? extends DataObject> selectedFiles) {
        if (selectedFiles.size() == 1) {
            DataObject dataObject = selectedFiles.iterator().next();
            RootNode node = new RootNode(dataObject, Children.LEAF);
            node.getFactory().register();
            rootNode = of(node);
            manager.setRootContext(node);
        } else {
            rootNode.ifPresent(n -> n.getFactory().cleanup());
            manager.setRootContext(Node.EMPTY);
        }
    }
}
