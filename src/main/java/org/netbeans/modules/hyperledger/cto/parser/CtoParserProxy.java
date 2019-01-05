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
package org.netbeans.modules.hyperledger.cto.parser;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.swing.event.ChangeListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.netbeans.modules.hyperledger.cto.grammar.CtoParser;
import org.netbeans.modules.hyperledger.cto.grammar.ErrorParserListener;
import org.netbeans.modules.hyperledger.cto.grammar.ParserListener;
import org.netbeans.modules.hyperledger.cto.grammar.ResourcesResult;
import org.netbeans.modules.hyperledger.cto.grammar.SyntaxError;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 *
 * @author mario.schroeder
 */
public class CtoParserProxy extends Parser {
    
    private final Function<String, CtoParser> parserProvider;
    
    private CtoParserResult parserResult;

    public CtoParserProxy(Function<String, CtoParser> parserProvider) {
        this.parserProvider = parserProvider;
    }

    @Override
    public void parse(Snapshot snapshot, Task task, SourceModificationEvent sme) throws ParseException {
        
        String text = snapshot.getText().toString();
        CtoParser ctoParser = parserProvider.apply(text);
        
        ParserListener listener = new ParserListener();
        ErrorParserListener errorListener = new ErrorParserListener();
        ctoParser.addParseListener(listener);
        ctoParser.addErrorListener(errorListener);
        //do the parsing
        ctoParser.modelUnit();
        
        ResourcesResult result = listener.getResult();
        List<SyntaxError> errors = errorListener.getSyntaxErrors();
        
        parserResult = new CtoParserResult(snapshot, result, errors);
    }

    @Override
    public Result getResult(Task task) throws ParseException {
        return parserResult;
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
    }

    public static class CtoParserResult extends Parser.Result {

        private boolean valid = true;
        private final ResourcesResult resourcesResult;
        private final List<SyntaxError> errors;

        public CtoParserResult(Snapshot snapshot, ResourcesResult resourcesResult, List<SyntaxError> errors) {
            super(snapshot);
            this.resourcesResult = resourcesResult;
            this.errors = errors;
        }
        
        public Optional<ResourcesResult> getResourcesResult() {
            if (!valid) {
                return empty();
            }
            return of(resourcesResult);
        }

        public List<SyntaxError> getErrors() {
            if(!valid) {
                return emptyList();
            }
            return errors;
        }
        
        @Override
        protected void invalidate() {
            valid = false;
        }

    }
}
