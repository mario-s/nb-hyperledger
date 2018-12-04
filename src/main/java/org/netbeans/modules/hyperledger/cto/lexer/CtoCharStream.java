package org.netbeans.modules.hyperledger.cto.lexer;

import java.util.ArrayDeque;
import java.util.Deque;
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
    private final Deque<Integer> markers = new ArrayDeque<>();

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
        read();
    }

    @Override
    public int LA(int ahead) {
        if (ahead == 0) {
            return 0;
        }

        int c = 0;
        for (int j = 0; j < ahead; j++) {
            c = read();
        }
        backup(ahead);

        return c;
    }

    @Override
    public int mark() {
        markers.push(index());
        return markers.size() - 1;
    }

    @Override
    public void release(int marker) {
        if(markers.size() < marker) {
            return;
        }
        
        //remove all markers from the given one
        for(int i = marker; i < markers.size(); i++) {
            markers.remove(i);
        }
    }

    @Override
    public int index() {
        return input.readLengthEOF();
    }

    @Override
    public void seek(int index) {
        int len = index();
        if (index < len) {
            //seek backward
            backup(len - index);
        } else {
            // seek forward
            while (len < index) {
                consume();
            }
        }
    }

    @Override
    public int size() {
        return -1; //unknown
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
