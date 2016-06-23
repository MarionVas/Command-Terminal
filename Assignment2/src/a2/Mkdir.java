package a2;

import java.util.*;

public class Mkdir implements CommandInterface {
  private FileSystem Manager;
  private String[] names;
  // List of all the special characters that cannot be used
  private String[] specialChar =
      new String[] {"/", "!", "@", "$", "&", "#", "*", "(", ")", "?", ":", "[",
          "]", "\"", "<", ">", "\'", "`", "\\", "|", "=", "{", "}", "/", ";"};

  public Mkdir(JFileSystem fileManager, String[] arg) {
    this.Manager = fileManager;
    this.names = arg;

  }
  public void executeFullPath(String name){
    String parentPath = name.substring(0, name.lastIndexOf("/"));
    if (Manager.checkValidPath(parentPath)) {
      int currDirIndex = name.lastIndexOf("/");
      String currDir = name.substring(currDirIndex + 1, name.length());
      Folder newFolder = new Folder(currDir, name);
      Manager.addFullPath(name);
      Folder parentFolder =
          (Folder) Manager.getObject(parentPath);
      parentFolder.addChildren(newFolder);
    } else if (parentPath.equals("")) {
      int currDirIndex = name.lastIndexOf("/");
      String currDir = name.substring(currDirIndex + 1, name.length());
      Folder newFolder = new Folder(currDir, name);
      Manager.addFullPath(name);
      Folder parentFolder =
          (Folder) Manager.getObject("/");
      parentFolder.addChildren(newFolder);

    } else {
      Output.printPathError();
    }
  }
  public String removeDots(String name){
    int lastIndex = 0;
    int count = 0;

    while (lastIndex != -1) {

      lastIndex = name.indexOf("..", lastIndex);

      if (lastIndex != -1) {
        count++;
        lastIndex += 2;
      }
    }
    String path = "";
    int indexDots = name.indexOf("/");
    if (Manager.getCurrPath().split("/").length - count == 1) {
      path = "/";
      name = name.substring(indexDots + 1, name.length());

    }
    for (int i = 1; i < Manager.getCurrPath().split("/").length
        - count; i++) {
      path = path + "/" + Manager.getCurrPath().split("/")[i];
      if (!name.startsWith("..")){
        path = path + "/" + name.substring(0, indexDots);
        i--;
      }
      name = name.substring(indexDots+1, name.length());
      indexDots = name.indexOf("/");

    }
    System.out.println(path + name);
    return path + name;
  }
  public void executeLocal(String name){
    boolean valid = true;
    for (int i = 0; i < specialChar.length; i++) {
      if (name.contains(specialChar[i])) {
        valid = false;
      }
    }
    if (valid) {
      String fullPath = "";
      if (Manager.getCurrPath().equals("/")) {
        fullPath = Manager.getCurrPath() + name;
      }

      else {
        fullPath = Manager.getCurrPath() + "/" + name;
      }
      Folder newFolder = new Folder(name, fullPath);
      Manager.addFullPath(fullPath);
      Folder parentFolder =
          (Folder) Manager.getObject(Manager.getCurrPath());
      parentFolder.addChildren(newFolder);
    } else {
      Output.printFileNameError();
    }
  }
  public void execute() {
    for (int index = 0; index < this.names.length; index++) {
      String name = this.names[index];
      if (name.endsWith("/")){
        name = name.substring(0, name.length()-1);
      }
      if (name.startsWith("/")) {
        if (Manager.checkValidPath(name)){
          Output.printError();
        }
        else {
          this.executeFullPath(name);
        }
      }
      else if (name.startsWith("..")) { 
        name = this.removeDots(name);
        if (Manager.checkValidPath(name)){
          Output.printError();
        }
        else {
          this.executeFullPath(name);
        }
      } else if (name.contains("/")) {
        name = Manager.getCurrPath() + "/" + name;
        if (Manager.checkValidPath(name)){
          Output.printError();
        }
        else {
          this.executeFullPath(name);
        }

      } else {
        if (Manager.checkValidPath(name)){
          Output.printError();
        }
        else {
          this.executeLocal(name);
        }
      }
    }
  }
 public static void main(String[] args) 
  {
    /*String s = "/eeee/qqqq/w";
    String s1 = "wefedsd";
    String s2 = "/eedd";
    String s3 = "/";
    System.out.println(s.split("/", -1)[0] + "   " + s1.split("/", -1)[0] + " " + s2.split("/", -1)[1] + " " + s3.split("/", -1)[2]);*/
   JFileSystem man = new JFileSystem();;
    Folder rootfold = new Folder("/", "/");
    man.setRoot(rootfold);
    String[] s = new String[1];
    s[0] = "qawsed";
    Mkdir mkdir = new Mkdir(man, s);
    mkdir.execute();
    System.out.println(((Folder) man.getObject("/qawsed")).getPath() + "  wew");
    s[0] = "rsxq";
    mkdir = new Mkdir(man, s);
    mkdir.execute();
    System.out.println(((Folder) man.getObject("/rsxq")).getPath() + "  AWR" );
    s[0] = "/rsxq/wer";
    mkdir = new Mkdir(man, s);
    mkdir.execute();
    man.setFullPath("/rsxq");
    s[0] = "../wer";
    mkdir = new Mkdir(man, s);
    mkdir.execute();
    //System.out.println(((Folder) man.getObject(2)).getPath() + "  wdw" );
    Folder fold = (Folder) man.getObject("/wer");
    System.out.println(fold.getName());
    man.setFullPath("/a/b/c/d/q/e");
    
  }
}
