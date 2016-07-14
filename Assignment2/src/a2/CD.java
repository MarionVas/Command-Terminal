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
  private final String stringToOutput = "";

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

  public String execute() {
    try{
      String fullPath = fileSystem.getFullPath(path);
      fileSystem.setFullPath(fullPath);
      fileSystem.setCurrFolder((Folder) fileSystem.getObject(fullPath));
    }catch(InvalidPath e){
      System.out.println("That was not a valid path.");
    }
    return stringToOutput;
  }

  /**
   * This function returns the instructions on how to use the command cat.
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
