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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.util.Pair;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/**
 *
 * @author mario.schroeder
 */
@ExtendWith(MockitoExtension.class)
public class MembersFactoryTest {
    
    @Mock
    private DataNode node;
    
    @Mock
    private DataObject dataObject;
    
    private MembersFactory classUnderTest;
    
    @BeforeEach
    public void setup() {
        String path = getClass().getResource("sample.cto").getFile();
        FileObject fileObject = FileUtil.toFileObject(new File(path));
        
        given(node.getDataObject()).willReturn(dataObject);
        given(dataObject.getPrimaryFile()).willReturn(fileObject);
        
        classUnderTest = new MembersFactory(node);
    }
    
    @Test
    @DisplayName("The factory should create 7 keys.")
    public void createKeys() {
        List<Pair<String,String>> toPopulate = new ArrayList<>();
        classUnderTest.createKeys(toPopulate);
        assertThat(toPopulate.size(), is(7));
    }
    
    @Test
    @DisplayName("The factory should update the display name of the root node.")
    public void updateDisplayname() {
        List<Pair<String,String>> toPopulate = new ArrayList<>();
        classUnderTest.createKeys(toPopulate);
        verify(node).setDisplayName(anyString());
    }
}
