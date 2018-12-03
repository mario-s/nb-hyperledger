package org.netbeans.modules.hyperledger.cto.lexer;

import java.util.Objects;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.misc.Interval;
import org.netbeans.spi.lexer.LexerInput;

/**
 *
 */
final class CtoCharStream implements CharStream {
    
    private final static String NAME = "CtoChar";

    private final LexerInput input;

    private int markDepth = 0;
    private int index = 0;

    public CtoCharStream(LexerInput input) {
        this.input = input;
    }

    @Override
    public String getText(Interval interval) {
        Objects.requireNonNull(interval, "Interval may not be null");
        if (interval.a < 0 || interval.b < interval.a - 1) {
            throw new IllegalArgumentException("Invalid interval!");
        }
        return input.readText(interval.a, interval.b).toString();
    }

    @Override
    public void consume() {
        input.read();
        index++;
    }

    @Override
    public int LA(int i) {
        if (i == 0) {
            return 0; 
        }

        int c = 0;
        for (int j = 0; j < i; j++) {
            c = read();
        }
        backup(i);

        return c;
    }

    @Override
    public int mark() {
        markDepth++;
        return markDepth;
    }

    @Override
    public void release(int marker) {
        // unwind any other markers made after m and release m
        markDepth = marker;
        // release this marker
        markDepth--;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public void seek(int index) {
        if (index < this.index) {
            backup(this.index - index);
            this.index = index;
            return;
        }

        // seek forward
        while (this.index < index) {
            consume();
        }
    }

    @Override
    public int size() {
        return -1; //?
    }

    @Override
    public String getSourceName() {
        return NAME;
    }

    private int read() {
        int result = input.read();
        if (result == LexerInput.EOF) {
            result = CharStream.EOF;
        }

        return result;
    }

    private void backup(int count) {
        input.backup(count);
    }
}
