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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.completion.Completion;
import org.netbeans.spi.editor.completion.CompletionItem;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.CompletionUtilities;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 */
public class CtoCompletionItem implements CompletionItem {
    
    private static Color SELCTED_COLOR = Color.decode("0x0000B2");
    private final String name;
    
    private ImageIcon icon;
    private int caretOffset;

    public CtoCompletionItem(String name) {
        this(name, "org/netbeans/modules/hyperledger/cto/value_16x16.png");
    }
    
    public CtoCompletionItem(String name, String iconPath) {
        this.name = name;
        if(iconPath != null) {
            this.icon = new ImageIcon(ImageUtilities.loadImage(iconPath));
        }
    }

    @Override
    public void defaultAction(JTextComponent jtc) {
        try {
            StyledDocument doc = (StyledDocument) jtc.getDocument();
            doc.insertString(caretOffset, name, null);
            Completion.get().hideAll();
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void processKeyEvent(KeyEvent ke) {
        //TODO
    }

    @Override
    public int getPreferredWidth(Graphics graphics, Font font) {
        return CompletionUtilities.getPreferredWidth(name, null, graphics, font);
    }

    @Override
    public void render(Graphics grphcs, Font font, Color frontCol, Color backCol, int width, int height, boolean selected) {
        CompletionUtilities.renderHtml(icon, name, null, grphcs, font,
            (selected ? Color.white : SELCTED_COLOR), width, height, selected);
    }

    @Override
    public CompletionTask createDocumentationTask() {
        return null;
    }

    @Override
    public CompletionTask createToolTipTask() {
        return null;
    }

    @Override
    public boolean instantSubstitution(JTextComponent jtc) {
        return false;
    }

    @Override
    public int getSortPriority() {
        return 100;
    }

    @Override
    public CharSequence getSortText() {
        return name;
    }

    @Override
    public CharSequence getInsertPrefix() {
        return name;
    }

}