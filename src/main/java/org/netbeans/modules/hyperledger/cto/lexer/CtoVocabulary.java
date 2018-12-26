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
package org.netbeans.modules.hyperledger.cto.lexer;

import org.antlr.v4.runtime.Vocabulary;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;

/**
 *
 * @author mario.schroeder
 */
public final class CtoVocabulary implements Vocabulary{
    
    private static final Vocabulary VOCABULARY = CtoLexer.VOCABULARY;

    @Override
    public int getMaxTokenType() {
        return VOCABULARY.getMaxTokenType();
    }

    @Override
    public String getLiteralName(int tokenType) {
        return VOCABULARY.getLiteralName(tokenType);
    }

    @Override
    public String getSymbolicName(int tokenType) {
        return VOCABULARY.getSymbolicName(tokenType);
    }

    @Override
    public String getDisplayName(int tokenType) {
        String name = VOCABULARY.getDisplayName(tokenType);
        return name.replaceAll("^\\'|\\'$", "");
    }
    
}
