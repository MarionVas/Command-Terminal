package a2;
import java.util.Vector;

import a2.Mkdir;
public class Copy {
  
  private JFileSystem insertedFileSystem;
  private String[] classPaths;
  private String stringToOutput = "";
  private boolean exisits = true;
  private Mkdir mkdir;
  private String newPath;
  private String oldPath;
  private String Path;
  private boolean firstPass = true;

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
      this.exisits = false;
      String[] toCreate = new String[]{classPaths[1]};
      mkdir = new Mkdir(this.insertedFileSystem, toCreate);
      mkdir.execute();
      try {
        newPath = this.insertedFileSystem.getFullPath(classPaths[1]);
      } catch (InvalidPath e1) {
        e1.printStackTrace();
      }
    }
    if (!this.newPath.startsWith(this.oldPath)){
      this.recurseLS(0, this.insertedFileSystem.getObject(oldPath));
    }
    else{
      System.err.println("Cannot copy a parent folder into it's child");
    }
    
    return "";
  }
  public void recurseLS(int childIndex, Item dirrOrFile) {
    // If the end of the subtree is reached
    String result = "";
    if (dirrOrFile == null) {
      result = "";
    }
    else if(dirrOrFile.getClass().equals(File.class)){
      if (!this.oldPath.equals("/")){
        this.Path = this.oldPath + "/" + dirrOrFile.getPath().split(this.oldPath)[1];
      }
      else{
        this.Path = this.oldPath + "/" + dirrOrFile.getPath().split(this.oldPath)[1];
      }
      File cpFile = new File(dirrOrFile.getName());
      cpFile.setBody(((File) dirrOrFile).getBody());
      cpFile.setPath(this.Path);
      String parentPath = this.oldPath.substring(0, this.oldPath.lastIndexOf("/"));
      this.insertedFileSystem.addFullPath(this.Path);
      ((Folder) this.insertedFileSystem.getObject(parentPath)).addChildren(cpFile);
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
          this.recurseLS(childIndex, allChildren.get(childIndex));
          this.recurseLS(childIndex + 1, dirrOrFile);
        } else if (childIndex < allChildren.size()) {
          this.recurseLS(0, allChildren.get(childIndex));
          this.recurseLS(childIndex + 1, dirrOrFile);
        }
      }
    }

  }
  public static void main(String[] args) 
  {
    
    JFileSystem jFileSystem = new JFileSystem();
    Folder rootfold = new Folder("/", "/");
    jFileSystem.setRoot(rootfold);

    String input = "";
    String[] a = new String [2];
    a[0] = "a";
    a[1] = "b";
    Mkdir mkdir = new Mkdir(jFileSystem, a);
    a = new String[1];
    a[0] = "a";
    CD cd = new CD(jFileSystem, a);
    mkdir.execute();
    cd.execute();
    a = new String[3];
    a[0] = "e";
    a[1] = "f";
    a[2] = "q";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();
    a = new String[1];
    a[0] = "e";
    cd = new CD(jFileSystem, a);
    cd.execute();
    a = new String[3];
    a[0] = "x";
    a[1] = "z";
    a[2] = "v";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();
    a = new String[1];
    a[0] = "x";
    cd = new CD(jFileSystem, a);
    cd.execute();
    a = new String[1];
    System.out.println(jFileSystem.getCurrPath());
    a[0] = "../v/../../././../qqq/./";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();
    a = new String[1];
    a[0] = "/";
    cd = new CD(jFileSystem, a);
    a[0] = "/a/e/x/PLSWORK";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();
    a[0] = "/a/e/z/PLSWORK3WEI";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();
    a[0] = "../../a/../";
    a = new String[3];
    a[0] = "-r";
    a[1] = "/a";
    a[2] = "/qqq";
    LS ls = new LS(jFileSystem, a);
    //System.out.println(ls.execute());
    a = new String[2];
    a[0] = "/a";
    a[1] = "/a/e";
    Copy q = new Copy(jFileSystem, a);
    q.execute();
    a = new String[2];
    a[0] = "-r";
    a[1] = "/";
    ls = new LS(jFileSystem, a);
    System.out.println(ls.execute());
    
  }
}
