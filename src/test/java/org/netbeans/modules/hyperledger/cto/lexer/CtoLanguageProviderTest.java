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
public class CtoLanguageProviderTest {

    private CtoLanguageProvider classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = new CtoLanguageProvider();
    }

    /**
     * Test of findLanguage method, of class CtoLanguageProvider.
     */
    @Test
    @DisplayName("It should return null when the mime is null.")
    public void findLanguage_MimeIsNull() {
        Language result = classUnderTest.findLanguage(null);
        assertThat(result, nullValue());
    }

    /**
     * Test of findLanguage method, of class CtoLanguageProvider.
     */
    @Test
    @DisplayName("It should return CtoLanguage when the mime is cto.")
    public void findLanguage_MimeIsCto() {
        String mime = FileType.MIME;
        Language result = classUnderTest.findLanguage(mime);
        assertThat(result.mimeType(), equalTo(mime));
    }

}
