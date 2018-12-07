/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.hyperledger.cto.completion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.spi.editor.completion.CompletionResultSet;

/**
 *
 */
@ExtendWith(MockitoExtension.class)
public class CtoCompletionQueryTest {
    @Mock
    private CompletionResultSet resultSet;
    
    private CtoCompletionQuery classUnderTest;
    
    @BeforeEach
    public void setUp() {
        classUnderTest = new CtoCompletionQuery();
    }
    
    @Test
    @DisplayName("The query should add items to the result set.")
    public void query_AddItems() {
        classUnderTest.query(resultSet, null, 0);
        verify(resultSet, times(2)).addAllItems(anyList());
    }
    

    
}
