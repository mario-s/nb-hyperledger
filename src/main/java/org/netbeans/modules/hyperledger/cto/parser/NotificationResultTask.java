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
import java.util.Set;
import javax.swing.text.Document;
import org.netbeans.modules.hyperledger.LookupContext;
import org.netbeans.modules.hyperledger.cto.CtoResource;
import org.netbeans.modules.hyperledger.cto.grammar.SyntaxError;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;

import static java.util.stream.Collectors.toList;

/**
 *
 * @author mario.schroeder
 */
public class NotificationResultTask extends ParserResultTask {
    
    @Override
    public void run(Parser.Result result, SchedulerEvent se) {
        CtoProxyParser.CtoParserResult ctoResult = (CtoProxyParser.CtoParserResult) result;
        
        Set<CtoResource> resources = ctoResult.getResources();
        if (!resources.isEmpty()) {
            LookupContext.INSTANCE.add(resources);
        }
        
        Document document = result.getSnapshot().getSource().getDocument(false);
        List<SyntaxError> syntaxErrors = ctoResult.getErrors();
        List<ErrorDescription> errorDescriptions = syntaxErrors.stream().map(e
                -> ErrorDescriptionFactory.createErrorDescription(
                        Severity.ERROR,
                        e.getMessage(),
                        document,
                        e.getLine())).collect(toList());
        HintsController.setErrors(document, "cto", errorDescriptions);
    }
    
    @Override
    public int getPriority() {
        return 100; //the lower, the higher the priority
    }
    
    @Override
    public Class<? extends Scheduler> getSchedulerClass() {
        return Scheduler.EDITOR_SENSITIVE_TASK_SCHEDULER;
    }
    
    @Override
    public void cancel() {
    }
}
