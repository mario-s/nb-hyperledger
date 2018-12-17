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
package org.netbeans.modules.hyperledger.project;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.modules.hyperledger.cto.FileType;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectFactory2;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

/**
 * Project factory for Hyperledger.
 * 
 * @author mario.schroeder
 */
@ServiceProvider(service = ProjectFactory.class)
public class HyperledgerProjectFactory implements ProjectFactory2{

    @Override
    public ProjectManager.Result isProject2(FileObject projectDirectory) {
        ProjectManager.Result result = null;
        if (isProject(projectDirectory)) {
            result = new ProjectManager.Result(FileType.icon());
        }
        return result;
    }

    @Override
    public boolean isProject(FileObject projectDirectory) {
        PackagesFinder finder = new PackagesFinder(projectDirectory);
        return finder.hasPackage() && finder.hasModel() && finder.hasLogic();
    }

    @Override
    public Project loadProject(FileObject projectDirectory, ProjectState state) throws IOException {
        return isProject(projectDirectory) ? new HyperledgerProject(projectDirectory, state) : null;
    }

    @Override
    public void saveProject(Project project) throws IOException, ClassCastException {
        //Nothing special
    }
    
}
