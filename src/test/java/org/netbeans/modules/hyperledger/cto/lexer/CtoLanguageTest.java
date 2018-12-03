package org.netbeans.modules.hyperledger.cto.lexer;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.netbeans.api.lexer.Language;
import org.netbeans.modules.hyperledger.cto.FileType;

/**
 *
 */
public class CtoLanguageTest {

    private CtoLanguage classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = new CtoLanguage();
    }

    /**
     * Test of findLanguage method, of class CtoLanguageProvider.
     */
    @Test
    @DisplayName("It should return LexerLanguage where the mime is cto.")
    public void getLexerLanguage() {
        String mime = FileType.MIME;
        Language result = classUnderTest.getLexerLanguage();
        assertThat(result.mimeType(), equalTo(mime));
    }

}
