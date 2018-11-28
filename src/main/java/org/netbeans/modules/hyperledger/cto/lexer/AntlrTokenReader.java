package org.netbeans.modules.hyperledger.cto.lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openide.util.Exceptions;

/**
 * This class will help if you change the ANTLR grammar. 
 */
public class AntlrTokenReader {
    
    private final Map<String, String> tokenTypes;
    private final List<CtoTokenId> tokens;
    
    public AntlrTokenReader() {
        tokenTypes = new HashMap<>();
        tokens = new ArrayList<>();
        
        tokenTypes.put("\'abstract\'", "keyword");
        
        tokenTypes.put("\'asset\'", "type");
        tokenTypes.put("\'participant\'", "type");
        
        tokenTypes.put("INTEGER", "number");
        tokenTypes.put("DOUBLE", "number");
    }
    
    /**
     * Reads the token file from the ANTLR parser and generates
     * appropriate tokens.
     *
     * @return
     */
    public List<CtoTokenId> readTokenFile() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inp = classLoader.getResourceAsStream("CtoLexer.tokens");
        BufferedReader input = new BufferedReader(new InputStreamReader(inp));
        readTokenFile(input);
        return tokens;
    }
    
     /**
     * Reads in the token file.
     *
     * @param buff
     */
    private void readTokenFile(BufferedReader buff) {
        String line = null;
        try {
            while ((line = buff.readLine()) != null) {
                int pos = line.lastIndexOf('=');
                String name = line.substring(0, pos);
                int tok = Integer.parseInt(line.substring(pos+1));
                
                //add it into the vector of tokens
                CtoTokenId id = createToken(name, tok);
                tokens.add(id);
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private CtoTokenId createToken(String name, int tok) {
        String tokenCategory = tokenTypes.get(name);
        if (tokenCategory != null) {
            //if the value exists, put it in the correct category
            return new CtoTokenId(name, tokenCategory, tok);
        }
        //if we don't recognize the token, consider it to a separator
        return new CtoTokenId(name, "separator", tok);
    }
}
