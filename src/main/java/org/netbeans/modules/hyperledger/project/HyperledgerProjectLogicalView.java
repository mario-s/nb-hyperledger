package org.netbeans.modules.hyperledger.project;

import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

import static org.openide.loaders.DataFolder.findFolder;

/**
 *
 * @author spindizzy
 */
class HyperledgerProjectLogicalView implements LogicalViewProvider {

    private final Project project;

    public HyperledgerProjectLogicalView(Project project) {
        this.project = project;
    }

    @Override
    public Node createLogicalView() {
            //Obtain the project directory's node:
            FileObject projectDirectory = project.getProjectDirectory();
            DataFolder projectFolder = findFolder(projectDirectory);
            Node nodeOfProjectFolder = projectFolder.getNodeDelegate();
            //Decorate the project directory's node: 
            Node node = new AbstractNode(Children.LEAF);
            node.setName(projectDirectory.getName());
            return node;
    }


    @Override
    public Node findPath(Node root, Object target) {
        return null;
    }
}
