package a2;
import java.util.Vector;

import a2.Mkdir;
public class Copy {
  
  private JFileSystem insertedFileSystem;
  private String[] classPaths;
  private String stringToOutput = "";
  private boolean exists = true;
  private Mkdir mkdir;
  private String newPath;
  private String oldPath;
  private String Path;
  private boolean firstPass = true;
  private Item oldPathType;
  private Item newPathType;

  public Copy(JFileSystem insertedFileSystem, String[] classPaths) {
    this.insertedFileSystem = insertedFileSystem;
    this.classPaths = classPaths;
  }
  
  public String execute(){
    try {
      oldPath = this.insertedFileSystem.getFullPath(classPaths[0]);
    } catch (InvalidPath e) {
      e.printStackTrace();
    }
    try {
      newPath = this.insertedFileSystem.getFullPath(classPaths[1]);
    } catch (InvalidPath e) {
      this.exists = false;
      if (!classPaths[1].endsWith(".txt")) {
        String[] toCreate = new String[]{classPaths[1]};
        mkdir = new Mkdir(this.insertedFileSystem, toCreate);
        mkdir.execute();
        try {
          newPath = this.insertedFileSystem.getFullPath(classPaths[1]);
        } catch (InvalidPath e1) {
          e1.printStackTrace();
        }
      }
      else{
        this.Path = this.newPath;
        File cpFile = new File(this.newPath.substring(this.newPath.lastIndexOf("/")));
        cpFile.setBody("");
        cpFile.setPath(this.Path);
        String parentPath = this.oldPath.substring(0, this.oldPath.lastIndexOf("/"));
        this.insertedFileSystem.addFullPath(this.Path);
        if(this.insertedFileSystem.checkValidPath(parentPath)){
          ((Folder) this.insertedFileSystem.getObject(parentPath)).addChildren(cpFile);
        }
        else{
          System.err.println("Invalid File path given");
        }
      }
      
    }
    this.oldPathType = this.insertedFileSystem.getObject(this.oldPath);
    this.newPathType = this.insertedFileSystem.getObject(this.newPath);
    if (oldPathType.getClass().equals(File.class) && newPathType.getClass().equals(File.class)){
      ((File) newPathType).setBody((((File)oldPathType).getBody()));
    }
    else if (oldPathType.getClass().equals(File.class) && newPathType.getClass().equals(Folder.class)){
      this.Path = this.newPath;
      File cpFile = new File(this.newPath.substring(this.newPath.lastIndexOf("/")));
      cpFile.setBody(((File) this.insertedFileSystem.getObject(this.oldPath)).getBody());
      cpFile.setPath(this.Path);
      String parentPath = this.oldPath.substring(0, this.oldPath.lastIndexOf("/"));
      this.insertedFileSystem.addFullPath(this.Path);
      parentPath = this.newPath.substring(0, this.newPath.lastIndexOf("/"));
      ((Folder) this.insertedFileSystem.getObject(parentPath)).addChildren(cpFile);
    }
    else if (!this.newPath.startsWith(this.oldPath)){
      this.recurseCopy(0, this.oldPathType);
    }
    else{
      System.err.println("Cannot copy a parent folder into it's child");
    }
    
    return "";
  }
  public void recurseCopy(int childIndex, Item dirrOrFile) {
    // If the end of the subtree is reached
    String result = "";
    if (dirrOrFile == null) {
      result = "";
    }
    else if(dirrOrFile.getClass().equals(File.class)){
      if (!dirrOrFile.getPath().equals(this.oldPath)){
        if (!this.oldPath.equals("/")){
          this.Path = this.newPath + "/" + dirrOrFile.getPath().split(this.oldPath)[1];
        }
        else{
          this.Path = "/" + dirrOrFile.getPath().split(this.oldPath)[1];
        }
        File cpFile = new File(dirrOrFile.getName());
        cpFile.setBody(((File) dirrOrFile).getBody());
        cpFile.setPath(this.Path);
        String parentPath = this.oldPath.substring(0, this.oldPath.lastIndexOf("/"));
        this.insertedFileSystem.addFullPath(this.Path);
        ((Folder) this.insertedFileSystem.getObject(parentPath)).addChildren(cpFile);
      }
    }
    else {
      if (!this.firstPass){
        if(!dirrOrFile.getPath().equals(this.oldPath)){
          if (!this.newPath.equals("/")){
            this.Path = this.newPath + dirrOrFile.getPath().split(this.oldPath)[1];
          }
          else{
            this.Path = "/" + dirrOrFile.getPath().split(this.oldPath)[1];
          }
          String[] arrayPath = new String[]{Path};
          this.mkdir = new Mkdir(this.insertedFileSystem, arrayPath);
          if (!this.insertedFileSystem.checkValidPath(Path)){
            mkdir.execute();
          }
        }
      }
      Vector<Item> allChildren = dirrOrFile.getChildren();
      this.firstPass = false;
      if (allChildren != null && allChildren.size() != 0) {
        if (childIndex == 0) {
          this.recurseCopy(childIndex, allChildren.get(childIndex));
          this.recurseCopy(childIndex + 1, dirrOrFile);
        } else if (childIndex < allChildren.size()) {
          this.recurseCopy(0, allChildren.get(childIndex));
          this.recurseCopy(childIndex + 1, dirrOrFile);
        }
      }
    }

  }
}
