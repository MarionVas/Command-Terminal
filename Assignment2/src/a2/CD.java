package a2;

public class CD implements CommandInterface {
  // CD collaborates with FileSystem
  private FileSystem fileSystem = new FileSystem(); // del equals
  // initialize a string variable for the path given as parameter
  private String path;

  /**
   * The constructor
   */

  public CD(String path) {
    // fileSystem = FileSystem.createFileSystemInstance();
    this.path = path;
  }

  /**
   * This function change current directory to the given directory. If the path
   * is ".." traverse to the parent directory. If the path is "." stay on the
   * current directory. If the path is valid traverse to the given directory and
   * if the path is invalid return an error statement.
   */

  public void execute() {
    // get the current path
    String currPath = fileSystem.getCurrPath();
    // check if the user wants the parent directory
    if (this.path == "..") {
      // check if the user is at the root
      if (currPath == "/") {
        // do nothing
      } else {
        // get the path to the parent directory
        currPath = currPath.substring(0, currPath.lastIndexOf("/"));
        // set the current path to the parent's directory
        fileSystem.setFullPath(currPath);
        // set the current folder to the parent's directory
        fileSystem.setCurrFolder((Folder) fileSystem.getObject(currPath));
      }
      // check if the user wants to stay on the same directory
    } else if (this.path == ".") {
      // do nothing
    } else {
      // check if the path given is valid
      boolean correctPath = fileSystem.checkValidPath(this.path);
      // if the path is not valid print an error message
      if (!correctPath) {
        Output.printPathError();
        // if the path is valid, change the current path to the given path
      } else {
        fileSystem.setFullPath(this.path);
        // change the current folder to the given folder at the given path
        fileSystem.setCurrFolder((Folder) fileSystem.getObject(this.path));

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
