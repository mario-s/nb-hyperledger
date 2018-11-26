package org.netbeans.modules.hyperledger.cto.lexer;

import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 *
 */
public class AntlrTokenReaderTest {

    private AntlrTokenReader classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = new AntlrTokenReader();
    }

    /**
     * Test of findLanguage method, of class CtoLanguageProvider.
     */
    @Test
    @DisplayName("It should return all tokens from the token File.")
    public void readToken() {
        List<CtoTokenId> result = classUnderTest.readTokenFile();
        assertThat(result.isEmpty(), is(false));
    }


}
