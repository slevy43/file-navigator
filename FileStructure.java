import java.util.Iterator;
import java.util.List;

public class FileStructure {

    // Instance variable
    private NLNode<FileObject> root;

    // Constructor
    public FileStructure (String fileObjectName) throws FileObjectException {

        try {
            // New FileObject representing file/folder
            FileObject fileObject = new FileObject(fileObjectName);

            // [root] node is given file/folder
            root = new NLNode<FileObject>(fileObject, null); // becomes [root] with 'null' parent

        // Recursive: [fileObject] is folder
            if (fileObject.isDirectory())  {
                folderTreeMaker(root);}

            // Catching if a problem occurred from creating [fileObject]
        } catch (FileObjectException e) {
            System.out.println("Problem constructing FileObject: " + e.getMessage());}


    } // Constructor

    // Helper method
    public void folderTreeMaker(NLNode<FileObject> currentFileObject) {

        // Base: [fileObject] is file
        if (currentFileObject.getData().isFile()){
            return;} // Done. Singular file as the root.

        // Recursive: creating nodes for each subsiding file
        Iterator<FileObject> containedObjects = currentFileObject.getData().directoryFiles();

        // While [fileObject] still has objects to iterate over:
        while (containedObjects.hasNext()) {

            // Retrieve the next file object contained in the iterator
            FileObject currentFile = containedObjects.next();

            // Node representing current [file]. Child of current [fileObject] structure
            NLNode<FileObject> newNode = new NLNode<>(currentFile, root);

            // Add the current [file] to the current [fileObject] structure
            currentFileObject.addChild(newNode);

            // Recursively explore and build this child's structure
            folderTreeMaker(newNode);}

    } // folderTreeMaker

    // Class methods
    public NLNode<FileObject> getRoot() {
        return root;}

    public Iterator<String> filesOfType (String type) {

        // Container storing names of correctly-typed files
        ListNodes<String> filenameContainer = new ListNodes<>();

        // Helper to traverse tree and collect correctly-typed filenames
        Iterator<String> container = typeCollector(filenameContainer, root, type);

        // Return Iterator [container] of correct path-names
        return container;}

    // filesOfType helper
    public Iterator<String> typeCollector(ListNodes<String> container, NLNode<FileObject> root, String type) {

        // (Base): [root] is a file
        if (root.getData().isFile()) {

            // File's full path name
            String absolutePath = root.getData().getLongName();

            // Add to [container] if pathname is the correct type
            if (absolutePath.contains(type)) {
                container.add(absolutePath);}

            } // Singular file added / ignored

        // (Recursive): adding each contained item to [container] (if applicable)
        else if (root.getData().isDirectory()) {
            Iterator<NLNode<FileObject>> containedObjects = root.getChildren();

            // While "this folder" still has objects to iterate over:
            while (containedObjects.hasNext()) {

                // Retrieve the next file object contained in the iterator
                NLNode<FileObject> currentFile = containedObjects.next();

                // Add to [container] if pathname is the correct type
                String currentPathname = currentFile.getData().getLongName();
                if (currentPathname.contains(type)) {
                    container.add(currentPathname); }

                // If not, recursively explore and build this child's structure
                else {typeCollector(container, currentFile, type);}}}

        // Converted to iterator and returned
        return container.getList();

    } // typeCollector

    public String findFile(String name) {

        // Empty string to be updated with desired fileName, or left empty
        String fileName = "";

        // Operation using tree and empty string to store desired fileName
        String foundFile = fileFinder(fileName, root, name);

        // Function returns full path of desired file
        return foundFile; }

    // findFile helper
    public String fileFinder(String fileName, NLNode<FileObject> root, String name) {

        // (Base): [root] is a file
        if (root.getData().isFile()) {

            // File's full path name
            String soughtFile = root.getData().getLongName();

            // Store [fileName] if the file is found
            if (soughtFile.contains(name)) {
                fileName = soughtFile;}}

        // (Recursive): looking through each file for match
        else if (root.getData().isDirectory()) {

            // Iterable list of file children
            Iterator<NLNode<FileObject>> containedObjects = root.getChildren();

            // While "this folder" still has objects to iterate over:
            while (containedObjects.hasNext()) {

                // Retrieve the next file object contained in the iterator
                NLNode<FileObject> currentFile = containedObjects.next();

                // Recursively explore tree for match
                fileName = fileFinder(fileName, currentFile, name);}}

        return fileName;

    } // fileFinder

} // FileStructure
