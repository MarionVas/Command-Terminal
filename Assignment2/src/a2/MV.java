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


        String existingItemPath = insertedFileSystem.getFullPath(classPaths[0]);
        Item existingItem = insertedFileSystem.getObject(existingItemPath);
        String parentPathExisting =
            existingItemPath.substring(0, existingItemPath.lastIndexOf("/"));
        Item parentExisting = insertedFileSystem.getObject(parentPathExisting);
        
        String specifiedItemPath =
            insertedFileSystem.getFullPath(classPaths[1]);
        String parentPathSpecified = specifiedItemPath.substring(0,
            specifiedItemPath.lastIndexOf("/"));
        
        if (checkPathExists(classPaths[1])) {
          
          Item specifiedItem = insertedFileSystem.getObject(specifiedItemPath);
          Item parentSpecified =
              insertedFileSystem.getObject(parentPathSpecified);

          if (specifiedItem instanceof Folder) {

            ((Folder) parentExisting).removeChildren(existingItem);
            setNewSpecifiedPath(existingItem, specifiedItemPath);
            ((Folder) specifiedItem).addChildren(existingItem);

          } else if (existingItem instanceof File
              && specifiedItem instanceof File) {
            ((Folder) parentExisting).removeChildren(existingItem);
            setNewSpecifiedPath(existingItem, parentPathSpecified);
            ((Folder) parentSpecified).removeChildren(specifiedItem);
            String fileName = existingItemPath.substring(
                existingItemPath.lastIndexOf("/") + 1,
                existingItemPath.length());
            existingItem.setName(fileName);
            ((Folder) parentSpecified).addChildren(existingItem);

          } else {
            System.out
                .print("Error: Cannot overwrite non-directory with directory");
          }

        } else {
          


        }
        System.out.print("Invalid command or path dne");
      }


    } catch (InvalidPath e) {
      System.out.print("Invalid path");
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

  private void setNewSpecifiedPath(Item newItem, String newItemParentPath) {
    String newParentPath = newItemParentPath + "/" + newItem.getName();
    newItem.setPath(newParentPath);
    if (!newItem.getChildren().isEmpty()) {
      for (Item child : newItem.getChildren()) {
        setNewSpecifiedPath(child, newParentPath);
      }
    }
  }

  
  
  public String manual() {
    // TODO Auto-generated method stub
    return null;
  }

}
