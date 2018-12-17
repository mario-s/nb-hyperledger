package org.netbeans.modules.hyperledger.project;

import org.netbeans.modules.hyperledger.cto.FileType;
import org.openide.filesystems.FileObject;

/**
 * The purpose of this is class is to search for the necessary elements for a
 * Haperledger project.
 *
 * @author spindizzy
 */
final class PackagesFinder {

    private static final String PACKAGE = "package.json";
    private static final String MODEL = "model";
    private static final String LOGIC = "lib";

    private final FileObject projectDirectory;

    PackagesFinder(FileObject projectDirectory) {
        this.projectDirectory = projectDirectory;
    }

    boolean hasPackage() {
        return projectDirectory.getFileObject(PACKAGE) != null;
    }

    boolean hasModel() {
        return projectDirectory.getFileObject(MODEL, "cto") != null;
    }

    boolean hasLogic() {
        return projectDirectory.getFileObject(LOGIC, "js") != null;
    }
}
