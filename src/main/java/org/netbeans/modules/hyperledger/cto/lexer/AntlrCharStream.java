package org.netbeans.modules.hyperledger.cto.lexer;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.misc.Interval;
import org.netbeans.spi.lexer.LexerInput;

/**
 *
 */
public class AntlrCharStream implements CharStream {

    private final LexerInput input;
    private final String name;
    private final boolean ignoreCase;

    private int line = 1;
    private int markDepth = 0;
    private int index = 0;
    private int charPositionInLine = 0;
    private List<CharStreamState> markers;

    public AntlrCharStream(LexerInput input, String name) {
        this(input, name, true);
    }
    
    public AntlrCharStream(LexerInput input, String name, boolean ignoreCase) {
        this.input = input;
        this.name = name;
        this.ignoreCase = ignoreCase;

        markers = new ArrayList<>();
        markers.add(null); // depth 0 means no backtracking, leave blank
    }

    @Override
    public String getText(Interval interval) {
        return input.readText(interval.a, interval.b).toString();
    }

    @Override
    public void consume() {
        int c = input.read();
        index++;
        charPositionInLine++;

        if (c == '\n') {
            line++;
            charPositionInLine = 0;
        }
    }

    @Override
    public int LA(int i) {
        if (i == 0) {
            return 0; // undefined
        }

        int c = 0;
        for (int j = 0; j < i; j++) {
            c = read();
        }
        backup(i);

        if (ignoreCase && (c != -1)){
            return Character.toLowerCase((char)c);
        } else {
            return c;
        }
    }

    @Override
    public int mark() {
        CharStreamState state = nextState();
        state.index = index;
        state.line = line;
        state.charPositionInLine = charPositionInLine;

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
        return name;
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

    private CharStreamState nextState() {
        markDepth++;
        CharStreamState state;
        if (markDepth >= markers.size()) {
            state = new CharStreamState();
            markers.add(state);
        } else {
            state = markers.get(markDepth);
        }
        return state;
    }

    private class CharStreamState {

        private int index;
        private int line;
        private int charPositionInLine;
    }

}
