package org.netbeans.modules.hyperledger.project;

import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author mario.schroeder
 */
class HyperledgerProject implements Project {

    private final FileObject projectDir;
    private final ProjectState projectState;
    private Lookup lookup;

    HyperledgerProject(FileObject projectDir, ProjectState state) {
        this.projectDir = projectDir;
        this.projectState = state;
    }

    @Override
    public FileObject getProjectDirectory() {
        return projectDir;
    }

    @Override
    public Lookup getLookup() {
        if (lookup == null) {
            lookup = Lookups.fixed(new Object[]{
                this,
            });
        }
        return lookup;
    }

    ProjectState getProjectState() {
        return projectState;
    }
}
