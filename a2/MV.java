package a2;

public class MV implements CommandInterface {
  private JFileSystem insertedFileSystem;
  private String[] classPaths;
  private String stringToOutput = "";


  public MV(JFileSystem insertedFileSystem, String[] classPaths) {
    this.insertedFileSystem = insertedFileSystem;
    this.classPaths = classPaths;
  }

  public String execute() {
    try {
    if (classPaths.length == 2 && checkPathExists(classPaths[0])) {

      /*
       * System.out.println(existingItemPath); String newItemPath =
       * insertedFileSystem.getFullPath(classPaths[1]);
       * System.out.println(newItemPath); String parentPathExisting =
       * existingItemPath.substring(0, existingItemPath.lastIndexOf("/"));
       * String parentPathNew = newItemPath.substring(0,
       * newItemPath.lastIndexOf("/")); System.out.println(parentPathExisting);
       * System.out.println(parentPathNew); Item parentExisting =
       * insertedFileSystem.getObject(parentPathExisting); Item newExistingItem
       * = insertedFileSystem.getObject(newItemPath);
       */

      String existingItemPath = insertedFileSystem.getFullPath(classPaths[0]);
      Item existingItem = insertedFileSystem.getObject(existingItemPath);
      String parentPathExisting = existingItemPath.substring(0, existingItemPath.lastIndexOf("/"));
      Item parentExisting = insertedFileSystem.getObject(parentPathExisting);
      
      if (checkPathExists(classPaths[1])) {
        String newItemPath = insertedFileSystem.getFullPath(classPaths[1]);
        Item specifiedItem = insertedFileSystem.getObject(newItemPath);
        if (specifiedItem instanceof Folder) {

          ((Folder) parentExisting).removeChildren(existingItem);
          // existingItem.setPath(newItemPath +);
          ((Folder) specifiedItem).addChildren(existingItem);
        }  
      }

      
    } else {
      System.out.print("Invalid command or path dne");
    }

    } catch (InvalidPath e){
      System.out.print("Invalid path");
    }
    return stringToOutput;
  }

  private boolean checkPathExists(String path) {
    try {
      insertedFileSystem.getFullPath(path);
      return true;

    } catch (InvalidPath e) {
      return false;
    }
  }

  public String manual() {
    // TODO Auto-generated method stub
    return null;
  }

}
