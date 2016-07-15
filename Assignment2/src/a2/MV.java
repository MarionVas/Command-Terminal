package a2;

public class MV implements CommandInterface {
  // Injected JFileSystem
  private JFileSystem insertedFileSystem;
  // User submitted arguments
  private String[] classPaths;
  // String returned by execute method to send to output
  private String stringToOutput = "";

  /**
   * Constructs a MV object which takes a JFileSystem for it to manipulate.
   * Also takes in an oldpath and newpath as parameters, that are used in the
   * execute method.
   * 
   * @param jFileSystem - FileSystem that the commands will act upon.
   * @param classPaths - Used to move items between specified paths
   */
  public MV(JFileSystem insertedFileSystem, String[] classPaths) {
    // Contains the jFileSystem to be manipulated
    this.insertedFileSystem = insertedFileSystem;
    // Contains paths of items to be moved around
    this.classPaths = classPaths;
  }

  /**
   * Constructs a MV object which takes a JFileSystem for it to manipulate.
   * Also takes in an oldpath and newpath as parameters, that are u
   * 
   * @param jFileSystem - FileSystem that the commands will act upon.
   * @param classPaths - Used to move items between specified paths
   */
  public String execute() {
    try {
      if (classPaths.length == 2 && checkPathExists(classPaths[0])
          && !isParent(classPaths[0], classPaths[1])) {


        String existingItemPath = insertedFileSystem.getFullPath(classPaths[0]);
        Item existingItem = insertedFileSystem.getObject(existingItemPath);
        String parentPathExisting =
            existingItemPath.substring(0, existingItemPath.lastIndexOf("/"));
        Item parentExisting = insertedFileSystem.getObject(parentPathExisting);

        String specifiedItemPath =
            insertedFileSystem.getFullPath(classPaths[1]);
        String parentPathSpecified =
            specifiedItemPath.substring(0, specifiedItemPath.lastIndexOf("/"));

        if (checkPathExists(classPaths[1])) {

          Item specifiedItem = insertedFileSystem.getObject(specifiedItemPath);
          Item parentSpecified =
              insertedFileSystem.getObject(parentPathSpecified);

          if (specifiedItem instanceof Folder) {
            ((Folder) parentExisting).removeChildren(existingItem);
            setNewSpecifiedPath(existingItem, specifiedItemPath,
                existingItem.getPath());
            ((Folder) specifiedItem).addChildren(existingItem);


          } else if (existingItem instanceof File
              && specifiedItem instanceof File) {
            ((Folder) parentExisting).removeChildren(existingItem);
            setNewSpecifiedPath(existingItem, parentPathSpecified,
                existingItem.getPath());
            ((Folder) parentSpecified).removeChildren(specifiedItem);
            String fileName = specifiedItemPath.substring(
                specifiedItemPath.lastIndexOf("/") + 1,
                specifiedItemPath.length());
            existingItem.setName(fileName);
            ((Folder) parentSpecified).addChildren(existingItem);

          } else {
            System.err.println("Cannot overwrite non-directory with directory");
          }

        } else {
          if ((isFile(specifiedItemPath) && existingItem instanceof File)
              | (!isFile(specifiedItemPath)
                  && existingItem instanceof Folder)) {
            System.out.println("heyaboi");
            String fileName = specifiedItemPath.substring(
                specifiedItemPath.lastIndexOf("/") + 1,
                specifiedItemPath.length());
            moveFileToNonExistingItemPath(fileName, parentPathSpecified,
                existingItem, parentExisting);


          } else {
            System.err.println("Cannot overwrite non-directory with directory");
          }
        }

      } else if (isParent(classPaths[0], classPaths[1])) {
        System.err.println("Cannot move parent directory into a subdirectory");
      } else {
        System.err.println("Invalid file path given");
      }


    } catch (

    InvalidPath e) {
      System.err.println("Invalid file path given");
    }
    return stringToOutput;
  }


  private boolean checkPathExists(String path) {
    boolean checkPath = false;
    try {
      checkPath = insertedFileSystem
          .checkValidPath(insertedFileSystem.getFullPath(path));
    } catch (InvalidPath e) {
      e.printStackTrace();
    }
    return checkPath;
  }

  private void moveFileToNonExistingItemPath(String movedItemName,
      String specifiedParentPath, Item currItem, Item parentCurrItem) {

    if (checkPathExists(specifiedParentPath)) {
      Item parentSpecified = insertedFileSystem.getObject(specifiedParentPath);
      ((Folder) parentCurrItem).removeChildren(currItem);
      currItem.setName(movedItemName);
      setNewSpecifiedPath(currItem, specifiedParentPath, currItem.getPath());
      ((Folder) parentSpecified).addChildren(currItem);

    } else {
      System.err.println("Invalid file path given");
    }
  }

  private void setNewSpecifiedPath(Item newItem, String newItemParentPath,
      String existingPath) {
    insertedFileSystem.removePaths(existingPath);
    String newPath = newItemParentPath + "/" + newItem.getName();
    newItem.setPath(newPath);
    insertedFileSystem.addFullPath(newPath);
    System.out.println(newItem.getPath());
    if (newItem instanceof Folder && !newItem.getChildren().isEmpty()) {
      for (Item child : newItem.getChildren()) {
        setNewSpecifiedPath(child, newPath, child.getPath());
      }
    }
  }

  private boolean isFile(String filePath) {
    boolean isFile = false;
    String fileName =
        filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
    if (fileName.contains(".")) {
      isFile = true;
    }
    return isFile;
  }

  private boolean isParent(String oldPath, String newPath) {
    boolean isParent = false;
    try {
      String oldFullPath = insertedFileSystem.getFullPath(oldPath);
      String newFullPath = insertedFileSystem.getFullPath(newPath);
      isParent = newFullPath.startsWith(oldFullPath);
    } catch (InvalidPath e) {
      System.err.println("Invalid path given");
    }
    return isParent;
  }

  public String manual() {
    // TODO Auto-generated method stub
    return null;
  }

}
