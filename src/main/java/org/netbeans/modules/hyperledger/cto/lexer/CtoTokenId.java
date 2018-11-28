package org.netbeans.modules.hyperledger.cto.lexer;

import org.netbeans.api.lexer.TokenId;

/**
 *
 */
public class CtoTokenId implements TokenId{
    
    private final String name;
    private final String primaryCategory;
    private final int id;
    
    public CtoTokenId(String name, String primaryCategory, int id) {
        this.name = name;
        this.primaryCategory = primaryCategory;
        this.id = id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int ordinal() {
        return id;
    }

    @Override
    public String primaryCategory() {
        return primaryCategory;
    }

    @Override
    public String toString() {
        return "CtoTokenId{" + "name=" + name + ", primaryCategory=" + primaryCategory + ", id=" + id + '}';
    }

}
