package a2;

import java.util.*;

public class Mkdir implements CommandInterface{
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

  public void execute() {
    for (int index = 0; index < this.names.length; index++){
      String name = this.names[index];
      if (name.startsWith("/")) {
        String parentPath = name.substring(0, name.lastIndexOf("/"));
        if (Manager.checkValidPath(parentPath)) {
          int currDirIndex = name.lastIndexOf("/");
          String currDir = name.substring(currDirIndex + 1, name.length());
          Folder newFolder = new Folder(currDir, name);
          Manager.addFullPath(name);
          if (name.split("/").length > 2) {
            Folder parentFolder = (Folder) Manager.getObject(parentPath);
            parentFolder.addChildren(newFolder);
          } else {
            Manager.add(newFolder);
            newFolder.isAtRoot(true);
          }
        } else {
          Output.printPathError();
        }
      } else if (name.startsWith("..")) {
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
        for (int i = 1; i < Manager.getCurrPath().split("/").length
            - count; i++) {
          path = path + "/" + Manager.getCurrPath().split("/")[i];
        }
        name = path + name;
        String parentPath = name.substring(0, name.lastIndexOf("/"));
        if (Manager.checkValidPath(parentPath)) {
          int currDirIndex = name.lastIndexOf("/");
          String currDir = name.substring(currDirIndex + 1, name.length());
          Folder newFolder = new Folder(currDir, name);
          Manager.addFullPath(name);
          if (name.split("/").length > 2) {
            Folder parentFolder = (Folder) Manager.getObject(parentPath);
            parentFolder.addChildren(newFolder);
          } else {
            Manager.add(newFolder);
            newFolder.isAtRoot(true);
          }
  
        } else {
          Output.printPathError();
        }
      } else if (name.contains("/")) {
        name = Manager.getCurrPath() + "/" + name;
  
  
        String parentPath = name.substring(0, name.lastIndexOf("/"));
        if (Manager.checkValidPath(parentPath)) {
          int currDirIndex = name.lastIndexOf("/");
          String currDir = name.substring(currDirIndex + 1, name.length());
          Folder newFolder = new Folder(currDir, name);
          Manager.addFullPath(name);
          if (name.split("/").length > 2) {
            Folder parentFolder = (Folder) Manager.getObject(parentPath);
            parentFolder.addChildren(newFolder);
          } else {
            Manager.add(newFolder);
            newFolder.isAtRoot(true);
          }
        } else {
          Output.printPathError();
        }
      } else {
        boolean valid = true;
        for (int i = 0; i < specialChar.length; i++) {
          if (name.contains(specialChar[i])) {
            valid = false;
          }
        }
        if (valid) {
          String fullPath = "";
          if (Manager.getCurrPath().split("/", -1).length <= 2) {
            System.out.println(Manager.getCurrPath().split("/", -1).length);
            fullPath = Manager.getCurrPath() + name;
          }
  
          else {
            fullPath = Manager.getCurrPath() + "/" + name;
          }
          Folder newFolder = new Folder(name, fullPath);
  
          if (fullPath.split("/").length > 2) {
            Manager.addFullPath(fullPath);
            Folder parentFolder =
                (Folder) Manager.getObject(Manager.getCurrPath());
            parentFolder.addChildren(newFolder);
          } else {
            Manager.add(newFolder);
            newFolder.isAtRoot(true);
          }
        } else {
          Output.printFileNameError();
        }
      }
    }
  }
}
