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


import java.util.Optional;
import static java.util.Optional.empty;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import org.openide.util.Exceptions;
import org.openide.util.Pair;

/**
 *
 */
interface CompletionFilter {

    char SPC = ' ';

    static class FilterResult {
        Optional<String> filter = empty();
        Pair<Integer, Integer> offset;
    }

    FilterResult filter(Document document, int offset);

    static class FilterImpl implements CompletionFilter {

        @Override
        public FilterResult filter(Document document, int offset) {
            String filter = null;
            int startOffset = offset - 1;

            try {
                StyledDocument styledDocument = (StyledDocument) document;
                int lineStartOffset = getRowFirstNonWhite(styledDocument, offset);
                char[] line = styledDocument.getText(lineStartOffset, offset - lineStartOffset).toCharArray();                
                int whiteOffset = indexOfWhite(line);

                filter = new String(line, whiteOffset + 1, line.length - whiteOffset - 1);
                startOffset = (whiteOffset > 0 ) ? lineStartOffset + whiteOffset + 1 : lineStartOffset;
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }

            FilterResult result = new FilterResult();
            result.filter = Optional.ofNullable(filter);
            result.offset = Pair.of(startOffset, offset);
            return result;
        }

        private int getRowFirstNonWhite(StyledDocument doc, int offset)
                throws BadLocationException {
            Element paragraph = doc.getParagraphElement(offset);
            int start = paragraph.getStartOffset();
            int end = paragraph.getEndOffset();
            while (start + 1 < end) {
                if (doc.getText(start, 1).charAt(0) != SPC) {
                    break;
                }
                start++;
            }
            return start;
        }

        private int indexOfWhite(char[] line) {
            for(int i = line.length - 1; i > -1; i--) {
                if (Character.isWhitespace(line[i])) {
                    return i;
                }
            }
            return -1;
        }
    }

}
