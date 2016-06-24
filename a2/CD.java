package a2;

public class CD implements CommandInterface {
  // CD collaborates with FileSystem
  private FileSystem fileSystem; // del equals
  // initialize a string variable for the path given as parameter
  private String[] path;

  /**
   * The constructor
   */

  public CD(JFileSystem manager, String[] path) {
    // fileSystem = FileSystem.createFileSystemInstance();
    this.path = path;
    this.fileSystem = manager;
  }

  /**
   * This function change current directory to the given directory. If the path
   * is ".." traverse to the parent directory. If the path is "." stay on the
   * current directory. If the path is valid traverse to the given directory and
   * if the path is invalid return an error statement.
   */

  public void execute() {
    // get the current path
    Folder currFolder = fileSystem.getCurrFolder();
    String currPath = fileSystem.getCurrPath();
    // check if the user wants the parent directory
    if (this.path[0].equals("..")) {
      // check if the user is at the root
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
      // check if the user wants to stay on the same directory
    } else if (this.path[0].equals(".")) {
      // do nothing
    } else {
      // check if the path given is valid
      boolean correctPath = fileSystem.checkValidPath(this.path[0]);
      if (!correctPath) {
        /*
         * if (this.fileSystem.getCurrPath().equals("/") &&
         * !path[0].contains("/")) { this.fileSystem.setFullPath("/" + path[0]);
         * this.fileSystem .setCurrFolder((Folder) fileSystem.getObject("/" +
         * path[0])); } else
         */if (currFolder.getAllChildrenNames().contains(path[0])) {
          Object pathObject = fileSystem.getObject(path[0]);
          if (pathObject.getClass().equals(Folder.class)) {
            if (this.fileSystem.getCurrPath().equals("/")) {
              fileSystem.setFullPath("/" + path[0]);
            } else {
              fileSystem.setFullPath(currPath + "/" + path[0]);
            }
            fileSystem.setCurrFolder((Folder) pathObject);
          } else {
            Output.printPathError();
          }
        } else {
          Output.printPathError();
        }

        // if the path is valid, change the current path to the given path
      } else {
        if (path[0].contains("..")) {

        } else {
          fileSystem.setFullPath(this.path[0]);
          // change the current folder to the given folder at the given path
          fileSystem.setCurrFolder((Folder) fileSystem.getObject(this.path[0]));
        }
      }
    }
  }

  /**
   * This function return the instructions on how to use the command CD.
   * 
   * @return a string telling users the how the command works
   */

  public String man() {
    return "Traverse through nested directories using the “cd”\n"
        + "command and a given directory “DIR” i.e. cd DIR. Instead of a\n"
        + "directory the user can pass through “..” which represents the\n"
        + "parent directory or “.” which represent the current directory";
  }

}
