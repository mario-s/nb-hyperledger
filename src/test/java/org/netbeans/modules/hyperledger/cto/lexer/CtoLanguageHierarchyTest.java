package org.netbeans.modules.hyperledger.cto.lexer;

import java.util.Collection;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 *
 */
public class CtoLanguageHierarchyTest {

    private CtoLanguageHierarchy classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = new CtoLanguageHierarchy();
    }

    @Test
    @DisplayName("It should return a non empty collection of tokens.")
    public void createTokenIds_NotEmpty() {
        Collection<CtoTokenId> result = classUnderTest.createTokenIds();
        assertThat(result.isEmpty(), is(false));
    }

}
