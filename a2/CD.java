package a2;

import a2.FileSystem;
import a2.Folder;
import a2.JFileSystem;
import a2.Output;


public class CD implements CommandInterface {
  private FileSystem fileSystem;
  // initialize a string variable for the path given as parameter
  private String[] parameter;
  private String path;
  private Folder currFolder;
  private String currPath;

  /**
   * The constructor
   * 
   * @param manager - The JFileSystem with all the file and folder
   * @param parameter - The string array with all the arguments the user enters
   */

  public CD(JFileSystem manager, String[] parameter) {
    this.parameter = parameter;
    this.fileSystem = manager;
    // set the path to be the first element of the parameter, since it should
    // only traverse through one path
    this.path = parameter[0];
    // get the current working directory as a folder
    this.currFolder = fileSystem.getCurrFolder();
    // get the current working directory as a String
    this.currPath = fileSystem.getCurrPath();
  }

  /**
   * This method will take the parameter passed into the constructor and
   * traverse into the path given if it is a valid path.
   */

  public void execute() {
    // if the path ends with a slash remove the slash
    if (path.endsWith("/") && (!path.equals("/"))) {
      path = path.substring(0, path.length() - 1);
    }
    // If more than one argument is passed into this command print an error
    if (this.parameter.length > 1) {
      Output.printPathError();
      // check if regular full path is given without ".." or "."
    } else if (this.path.startsWith("/") && (!this.path.contains(".."))
        && (!this.path.contains("."))) {
      // call method that traverses full path
      traverseFullPath(this.path);
      // check if the user wants only the parent directory
    } else if (this.path.equals("..")) {
      // call method that traverses to the parent folder
      traverseParent();
      // check if the user wants to stay on the current folder
    } else if (this.path.equals(".")) {
      // do nothing
    } else {
      // split the String at the slashes
      String[] folderNames = path.split("/");
      // start at the current path
      String fullPath = currPath;
      for (String eachName : folderNames) {
        // if the current String is ".." traverse to the parent folder
        if (eachName.equals("..")) {
          traverseParent();
          // update the current path
          fullPath = fileSystem.getCurrPath();
          // if the current String is "." do nothing
        } else if (eachName.equals(".")) {
          continue;
        } else {
          // check if user is at the root
          if (fullPath.equals("/")) {
            // update the full path so it includes the folder
            fullPath += eachName;
            // traverse into the full path
            traverseFullPath(fullPath);
          } else {
            // if the suer is not at the root, add a slash before the folder
            // name and update the full path
            fullPath += "/" + eachName;
            // traverse into the full path
            traverseFullPath(fullPath);
          }
        }
      }
    }
  }

  /**
   * This method will take a String path and traverse into to the end of the
   * path if it is valid and will print an error statement if it is not. This
   * method will not accept paths with ".." and "." in the middle of the path
   * 
   * @param path - this is a String representation of the path that needs to be
   *        traversed to
   */

  private void traverseFullPath(String path) {
    // check if the path is a valid path
    boolean correctPath = fileSystem.checkValidPath(path);
    if (correctPath) {
      // if the path is a valid path, set the current path in the fileSystem
      // to the path given
      fileSystem.setFullPath(path);
      // change the current folder to the given folder at the given path
      fileSystem.setCurrFolder((Folder) fileSystem.getObject(path));
    } else {
      // if the path is not a valid path, print an error
      Output.printPathError();;
    }
  }

  /**
   * This method will traverse into the parent directory of the current
   * directory
   */

  private void traverseParent() {
    // get the updated current path in the fileSystem
    currPath = fileSystem.getCurrPath();
    if (currPath == "/") {
      // do nothing
    } else {
      // get the path to the parent directory
      int indexLastSymbol = currPath.lastIndexOf("/");
      // check if the parent is the root
      if (indexLastSymbol == 0) {
        // set the string path to the root
        currPath = "/";
        // set the current path in the fileSystem to the root
        fileSystem.setFullPath(currPath);
        // change the current folder to the root folder
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
  
  /**
   * This function return the instructions on how to use the command cat.
   * 
   * @return a string telling users the how the command works
   */

  public String manual() {
    return "cd DIR - Changes the current directory to DIR, which may be a\n"
        + "specified full path or a path relative to the current directory.\n"
        + "'..' refers to the parent directory while '.' refers to the\n"
        + " current.\n";
  }
}