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

import java.util.function.Function;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.hyperledger.cto.grammar.CtoParser;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;

/**
 *
 * @author mario.schroeder
 */
public class CtoParserProxy extends Parser {

    private Snapshot snapshot;
    private CtoParser ctoParser;
    private Function<String, CtoParser> parserProvider;

    public CtoParserProxy(Function<String, CtoParser> parserProvider) {
        this.parserProvider = parserProvider;
    }

    @Override
    public void parse(Snapshot snapshot, Task task, SourceModificationEvent sme) throws ParseException {
        this.snapshot = snapshot;

        String text = snapshot.getText().toString();
        ctoParser = parserProvider.apply(text);
        ctoParser.modelUnit();
    }

    @Override
    public Result getResult(Task task) throws ParseException {
        return new CtoParserResult(snapshot, ctoParser);
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
    }

    public static class CtoParserResult extends Parser.Result {

        private boolean valid = true;
        private CtoParser ctoParser;

        public CtoParserResult(Snapshot snapshot, CtoParser ctoParser) {
            super(snapshot);
            this.ctoParser = ctoParser;
        }

        @Override
        protected void invalidate() {
            valid = false;
        }

    }
}
