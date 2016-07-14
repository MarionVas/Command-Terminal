package a2;

public class MV implements CommandInterface {
  private JFileSystem insertedFileSystem;
  private String[] classPaths;


  public MV(JFileSystem insertedFileSystem, String[] classPaths) {
    this.insertedFileSystem = insertedFileSystem;
    this.classPaths = classPaths;
  }

  public void execute() {
    try {
      if (classPaths.length == 2) {
        String existingItemPath = insertedFileSystem.getFullPath(classPaths[0]);
        String newItemPath = insertedFileSystem.getFullPath(classPaths[1]);
        String parentPathExisting = existingItemPath.substring(0, existingItemPath.lastIndexOf("/"));
        String parentPathNew = newItemPath.substring(0, newItemPath.lastIndexOf("/"));
        
        Item existingItem = insertedFileSystem.getObject(existingItemPath);
        Item parentExisting = insertedFileSystem.getObject(parentPathExisting);
        Item parentNew = insertedFileSystem.getObject(parentPathNew);
        
        ((Folder) parentExisting).removeChildren(existingItem);
        existingItem.setPath(newItemPath);
        ((Folder) parentNew).addChildren(existingItem);
      
      } else {
        System.out.print("Invalid command");
      }
    } catch (InvalidPath e) {
      System.out.print("Invalid path");
    }
  }

  public String manual() {
    // TODO Auto-generated method stub
    return null;
  }

}
