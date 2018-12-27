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
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Utils;

/**
 *
 * @author mario.schroeder
 */
final class StringStream implements CharStream {
    
    /**
     * The data being scanned
     */
    protected char[] data;

    /**
     * How many characters are actually in the buffer
     */
    protected int countChars;

    /**
     * 0..countChars-1 index into string of next char
     */
    protected int current = 0;

    StringStream(String content) {
        this.data = content.toCharArray();
        this.countChars = data.length;
    }

    @Override
    public String getText(Interval interval) {
        int start = interval.a;
        int stop = interval.b;
        if (stop >= countChars) {
            stop = countChars - 1;
        }
        int count = stop - start + 1;
        if (start >= countChars) {
            return "";
        }
        return new String(data, start, count);
    }

    @Override
    public String getSourceName() {
        return String.class.getSimpleName();
    }

    @Override
    public void consume() {
        if (current >= countChars) {
            assert LA(1) == IntStream.EOF;
            throw new IllegalStateException("cannot consume EOF");
        }

        if (current < countChars) {
            current++;
        }
    }

    @Override
    public int LA(int i) {
        if (i == 0) {
            return 0; // undefined
        }
        if (i < 0) {
            i++; // e.g., translate LA(-1) to use offset i=0; then data[current+0-1]
            if ((current + i - 1) < 0) {
                return IntStream.EOF; // invalid; no char before first char
            }
        }

        if ((current + i - 1) >= countChars) {
            return IntStream.EOF;
        }
        return data[current + i - 1];
    }

    public int LT(int i) {
        return LA(i);
    }

    @Override
    public int index() {
        return current;
    }

    @Override
    public int size() {
        return countChars;
    }

    /**
     * mark/release do nothing; we have entire buffer
     */
    @Override
    public int mark() {
        return -1;
    }

    @Override
    public void release(int marker) {
    }

    /**
     * consume() ahead until p==index; can't just set p=index as we must update
     * line and charPositionInLine. If we seek backwards, just set p
     */
    @Override
    public void seek(int index) {
        if (index <= current) {
            current = index; // just jump; don't update stream state (line, ...)
            return;
        }
        // seek forward, consume until current hits index or countChars (whichever comes first)
        index = Math.min(index, countChars);
        while (current < index) {
            consume();
        }
    }

    @Override
    public String toString() {
        return new String(data);
    }
}
