package org.netbeans.modules.hyperledger.cto.lexer;

import org.netbeans.api.lexer.TokenId;

/**
 *
 */
public enum CtoTokenId implements TokenId {
    
    ABSTRACT("abstract", "keyword"),
    ASSET("asset", "keyword"),
    CLASS("class", "keyword"),
    CONCEPT("concept", "keyword"),
    DEFAULT("default", "keyword"),
    ENUM("enum", "keyword"),
    EVENT("event", "keyword"),
    EXTENDS("extends", "keyword"),
    IDENTIFIED("identified by", "keyword"),
    IMPORT("import", "keyword"),
    NAMESPACE("namespace", "keyword"),
    OPTIONAL("optional", "keyword"),
    PARTICIPANT("participant", "keyword"),
    RANGE("range", "keyword"),
    REGEX("regex", "keyword"),
    TRANSACTION("transaction", "keyword"),
    
    BOOLEAN("Boolean", "primitive"),
    DATE_TIME("DateTime", "primitive"),
    DOUBLE("Double", "primitive"),
    INTEGER("Integer", "primitive"),
    LONG("Long", "primitive"),
    STRING("String", "primitive"),
    
    LPAREN("(", "separator"),
    RPAREN(")", "separator"),
    LBRACE("{", "separator"),
    RBRACE("}", "separator"),
    LBRACKET("[", "separator"),
    RBRACKET("]", "separator"),
    SEMI(";", "operator"),
    COMMA(",", "operator"),
    DOT(".", "operator"),
    COLON(":", "operator"),
    ASSIGN("=", "operator"),
    STAR("*", "operator"),
    AT("@", "operator"),
    TRIPLE_DOT("...", "operator"),
    REF("-->", "field"),
    FIELD("o", "field");

    private final String fixedText;
    private final String primaryCategory;

    CtoTokenId(String fixedText, String primaryCategory) {
        this.fixedText = fixedText;
        this.primaryCategory = primaryCategory;
    }

    public String fixedText() {
        return fixedText;
    }

    @Override
    public String primaryCategory() {
        return primaryCategory;
    }

    @Override
    public String toString() {
        return "CtoTokenId{" + "text=" + fixedText + ", primaryCategory=" + primaryCategory + '}';
    }

}
