package a2;

import a2.FileSystem;
import a2.Folder;
import a2.JFileSystem;
import a2.Output;

public class CD implements CommandInterface {
  private FileSystem fileSystem; // del equals
  // initialize a string variable for the path given as parameter
  private String[] parameter;
  private String path;
  private Folder currFolder;
  private String currPath;

  /**
   * The constructor
   */

  public CD(JFileSystem manager, String[] parameter) {
    this.parameter = parameter;
    this.fileSystem = manager;
    this.path = parameter[0];
    this.currFolder = fileSystem.getCurrFolder();
    this.currPath = fileSystem.getCurrPath();
  }

  public void execute() {
    // get the current path
    // get the current directory
    if (this.parameter.length > 1) {
      Output.printPathError();
    } else if (this.path.startsWith("/") && (!this.path.contains(".."))
        && (!this.path.contains("."))) {
      traverseFullPath(this.path);
    } else if (this.path.equals("..")) {
      // check if the user is at the root
      traverseParent();
    } else if (this.path.equals(".")) {
      // do nothing
    } else {
      int slash = this.path.indexOf("/");
      if (slash == -1) {
        traverseSingleFolder(this.path);
      } else if ((!(slash == 0)) && (!this.path.contains(".."))
          && (!this.path.contains("."))) {
        if (currPath.equals("/")) {
          String fullPath = currPath + path;
          traverseFullPath(fullPath);
        } else {
          String fullPath = currPath + "/" + path;
          traverseFullPath(fullPath);
        }
      } else {
        String[] folderNames = path.split("/");
        String fullPath = currPath;
        for (String eachName : folderNames) {
          if (eachName.equals("..")) {
            traverseParent();
            fullPath = fileSystem.getCurrPath();
          } else if (eachName.equals(".")) {
            continue;
          } else {
            if (fullPath.equals("/")) {
              fullPath += eachName;
              traverseFullPath(fullPath);
            } else {
              fullPath += "/" + eachName;
              traverseFullPath(fullPath);
            }
          }
        }
      }
      // finish code here a/b/../b/c/../c/d
    }
  }

  private void traverseFullPath(String path) {
    boolean correctPath = fileSystem.checkValidPath(path);
    if (correctPath) {
      fileSystem.setFullPath(path);
      // change the current folder to the given folder at the given path
      fileSystem.setCurrFolder((Folder) fileSystem.getObject(path));
    } else {
      Output.printPathError();;
    }
  }

  private void traverseParent() {
    currPath = fileSystem.getCurrPath();
    if (currPath == "/") {
      // do nothing
    } else {
      // get the path to the parent directory
      int indexLastSymbol = currPath.lastIndexOf("/");

      if (indexLastSymbol == 0) {
        currPath = "/";
        fileSystem.setFullPath(currPath);
        fileSystem.setCurrFolder((Folder) fileSystem.getObject(currPath));
      } else {
        currPath = currPath.substring(0, indexLastSymbol);
        // set the current path to the parent's directory
        fileSystem.setFullPath(currPath);
        // set the current folder to the parent's directory
        fileSystem.setCurrFolder((Folder) fileSystem.getObject(currPath));
      }
    }
  }

  private void traverseSingleFolder(String path) {
    if (currFolder.getAllChildrenNames().contains(path)) {
      Object pathObject = fileSystem.getObject(path);
      if (pathObject.getClass().equals(Folder.class)) {
        if (this.fileSystem.getCurrPath().equals("/")) {
          fileSystem.setFullPath("/" + path);
        } else {
          fileSystem.setFullPath(currPath + "/" + path);
        }
        fileSystem.setCurrFolder((Folder) pathObject);
      } else {
        Output.printPathError();
      }
    } else {
      Output.printPathError();
    }
  }
}

