package org.netbeans.modules.hyperledger.cto.lexer;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.netbeans.api.lexer.Language;
import org.netbeans.modules.hyperledger.cto.FileType;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;

/**
 *
 */
public class CtoLanguageHierarchyTest {

    private CtoLanguageHierarchy classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = new CtoLanguageHierarchy();
    }

    /**
     * Test of findLanguage method, of class CtoLanguageProvider.
     */
    @Test
    @DisplayName("It should return a token with keyword category for asset.")
    public void getToken_AssetKeyword() {
        CtoTokenId result = classUnderTest.getToken(CtoLexer.ASSET);
        assertThat(result.primaryCategory(), equalTo(CtoLanguageHierarchy.Category.keyword.name()));
    }

     /**
     * Test of findLanguage method, of class CtoLanguageProvider.
     */
    @Test
    @DisplayName("It should return a token with field category for a reference.")
    public void getToken_RefField() {
        CtoTokenId result = classUnderTest.getToken(CtoLexer.REF);
        assertThat(result.primaryCategory(), equalTo(CtoLanguageHierarchy.Category.field.name()));
    }

}
