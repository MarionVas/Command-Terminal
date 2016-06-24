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

  public void executeFullPath(String name) {
    String parentPath = name.substring(0, name.lastIndexOf("/"));
    if (Manager.checkValidPath(parentPath)) {
      int currDirIndex = name.lastIndexOf("/");
      String currDir = name.substring(currDirIndex + 1, name.length());
      Folder newFolder = new Folder(currDir, name);
      Manager.addFullPath(name);
      Folder parentFolder = (Folder) Manager.getObject(parentPath);
      parentFolder.addChildren(newFolder);
    } else if (parentPath.equals("")) {
      int currDirIndex = name.lastIndexOf("/");
      String currDir = name.substring(currDirIndex + 1, name.length());
      Folder newFolder = new Folder(currDir, name);
      Manager.addFullPath(name);
      Folder parentFolder = (Folder) Manager.getObject("/");
      parentFolder.addChildren(newFolder);

    } else {
      Output.printPathError();
    }
  }

  public String removeDots(String name) {
    int endIndex = 0;
    int numOfOccurences = 0;

    while (endIndex != -1) {

      endIndex = name.indexOf("..", endIndex);

      if (endIndex != -1) {
        numOfOccurences++;
        endIndex += 2;
      }
    }
    String path = Manager.getCurrPath();
    int indexDots = name.indexOf("/");
    for (int i = 0; i < numOfOccurences; i++) {
      if (!name.startsWith("..") && name.contains("..")) {
        path = path + "/" + name.substring(0, indexDots);
        i--;
      } else {
        try {
          path = path.substring(0, path.lastIndexOf("/"));
        } catch (Exception e) {
          path = "//////////";
          break;
        }
      }
      name = name.substring(indexDots + 1, name.length());
      indexDots = name.indexOf("/");

    }
    return path + "/" + name;
  }

  public void executeLocal(String name) {
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
      Folder parentFolder = (Folder) Manager.getObject(Manager.getCurrPath());
      parentFolder.addChildren(newFolder);
    } else {
      Output.printFileNameError();
    }
  }

  public void execute() {
    for (int index = 0; index < this.names.length; index++) {
      String name = this.names[index];
      if (name.endsWith("/")) {
        name = name.substring(0, name.length() - 1);
      }
      if (name.startsWith("/")) {
        if (Manager.checkValidPath(name)) {
          Output.printError();
        } else {
          this.executeFullPath(name);
        }
      } else if (name.startsWith("..")) {
        name = this.removeDots(name);
        if (Manager.checkValidPath(name)) {
          Output.printError();
        } else {
          this.executeFullPath(name);
        }
      } else if (name.contains("/")) {
        if (Manager.getCurrPath().equals("/")){
          name = "/" + name;
        }
        else {
          name = Manager.getCurrPath() + "/" + name;
        }
        
        if (Manager.checkValidPath(name)) {
          Output.printError();
        } else {
          this.executeFullPath(name);
        }

      } else {
        if (Manager.checkValidPath(name)) {
          Output.printError();
        } else {
          this.executeLocal(name);
        }
      }
    }
  }

}
