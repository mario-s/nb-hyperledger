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

import java.io.IOException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.misc.Utils;

/**
 *
 * @author mario.schroeder
 */
class FileCharStream extends ANTLRInputStream {

    private final String fileName;

    public FileCharStream(String fileName) throws IOException {
        this.fileName = fileName;
        load();
    }
    
    private void load() throws IOException {
        data = Utils.readFile(fileName, null);
        this.n = data.length;
    }

    @Override
    public String getSourceName() {
        return fileName;
    }
}
