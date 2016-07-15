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
        String parentPathSpecified =
            specifiedItemPath.substring(0, specifiedItemPath.lastIndexOf("/"));

        if (checkPathExists(classPaths[1])) {

          Item specifiedItem = insertedFileSystem.getObject(specifiedItemPath);
          Item parentSpecified =
              insertedFileSystem.getObject(parentPathSpecified);

          if (specifiedItem instanceof Folder) {
            System.out.println("OVA HER");
            ((Folder) parentExisting).removeChildren(existingItem);
            setNewSpecifiedPath(existingItem, specifiedItemPath);
            System.out.println(existingItem.getPath());
            ((Folder) specifiedItem).addChildren(existingItem);
            System.out.println(specifiedItem.getPath());

          } else if (existingItem instanceof File
              && specifiedItem instanceof File) {
            ((Folder) parentExisting).removeChildren(existingItem);
            setNewSpecifiedPath(existingItem, parentPathSpecified);
            System.out.println(existingItem.getPath());
            ((Folder) parentSpecified).removeChildren(specifiedItem);
            String fileName = specifiedItemPath.substring(
                specifiedItemPath.lastIndexOf("/") + 1,
                specifiedItemPath.length());
            existingItem.setName(fileName);
            ((Folder) parentSpecified).addChildren(existingItem);

          } else {
            System.out
                .println("Error: Cannot overwrite non-directory with directory");
          }

        } else {
          if (isFile(specifiedItemPath) && existingItem instanceof File) {
            String fileName = specifiedItemPath.substring(
                specifiedItemPath.lastIndexOf("/") + 1,
                specifiedItemPath.length());
            moveFileToNonExistingItemPath(fileName, parentPathSpecified,
                existingItem, parentExisting);

          } else if (!isFile(specifiedItemPath)) {
            String folderName = existingItemPath.substring(
                existingItemPath.lastIndexOf("/") + 1,
                existingItemPath.length());
            moveFileToNonExistingItemPath(folderName, specifiedItemPath,
                existingItem, parentExisting);
          } else {
            System.out
                .print("Error: Cannot overwrite non-directory with directory");
          }
        }


      } else {
        System.out.println("Invalid command or path dne");
      }


    } catch (

    InvalidPath e) {
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

  private void moveFileToNonExistingItemPath(String movedItemName,
      String specifiedParentPath, Item currItem, Item parentCurrItem) {

    try {
      if (checkPathExists(specifiedParentPath)) {
        Item parentSpecified =
            insertedFileSystem.getObject(specifiedParentPath);
        ((Folder) parentCurrItem).removeChildren(currItem);
        setNewSpecifiedPath(currItem, specifiedParentPath);
        currItem.setName(movedItemName);
        ((Folder) parentSpecified).addChildren(currItem);

      } else {
        String[] fileNamePara = {movedItemName};
        Mkdir makeParentSpecified = new Mkdir(insertedFileSystem, fileNamePara);
        makeParentSpecified.executeFullPath(specifiedParentPath);
        Item parentSpecified =
            insertedFileSystem.getObject(specifiedParentPath);
        ((Folder) parentCurrItem).removeChildren(currItem);
        setNewSpecifiedPath(currItem, specifiedParentPath);
        currItem.setName(movedItemName);

        ((Folder) parentSpecified).addChildren(currItem);
      }
    } catch (InvalidPath e) {
      System.out.println("Invalid Path");
    }
  }

  private void setNewSpecifiedPath(Item newItem, String newItemParentPath) {
    String newParentPath = newItemParentPath + "/" + newItem.getName();
    newItem.setPath(newParentPath);
    System.out.println(newItem.getPath());
    if (newItem instanceof Folder && !newItem.getChildren().isEmpty()) {
      for (Item child : newItem.getChildren()) {
        setNewSpecifiedPath(child, newParentPath);
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



  public String manual() {
    // TODO Auto-generated method stub
    return null;
  }

}
