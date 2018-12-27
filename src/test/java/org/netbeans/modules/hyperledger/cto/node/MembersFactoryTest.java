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
package org.netbeans.modules.hyperledger.cto.node;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.Pair;

/**
 *
 * @author mario.schroeder
 */
@ExtendWith(MockitoExtension.class)
public class MembersFactoryTest {
    
    @Mock
    private FileObject fileObject;
    
    @Mock
    private DataObject dataObject;
    
    @InjectMocks
    private MembersFactory classUnderTest;
    
    @BeforeEach
    public void setup() {
        String path = getClass().getResource("sample.cto").getPath();
        given(fileObject.getPath()).willReturn(path);
        given(dataObject.getPrimaryFile()).willReturn(fileObject);
    }
    
    @Test
    @DisplayName("The factory should create keys.")
    public void createKeys_NotEmpty() {
        List<Pair<String,String>> toPopulate = new ArrayList<>();
        classUnderTest.createKeys(toPopulate);
        assertThat(toPopulate.isEmpty(), is(false));
    }
}
