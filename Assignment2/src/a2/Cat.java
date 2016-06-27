package a2;

import java.util.Vector;

public class Cat implements CommandInterface {
  private FileSystem fileSystem;
  private String[] fileNames;
  private String stringToOutput = "";

  /**
   * The constructor
   * 
   * @param manager - The JFileSystem with all the file and folder
   * @param files - The string array with all the file names the user enters
   */

  public Cat(JFileSystem manager, String[] files) {
    this.fileSystem = manager;
    this.fileNames = files;
  }

  /**
   * This method will take the parameter passed into the constructor and read
   * the contents of those files in the current directory. If the files do not
   * exist, an error message will be printed
   */

  public void execute() {
    // get the current working directory
    Folder currFolder = fileSystem.getCurrFolder();
    // check if the user wants to read one file
    if (this.fileNames.length == 1) {
      // get the file the user wants to read
      File file = (File) currFolder.getFile(fileNames[0]);
      // if the file does not exist print an error message
      if (file == null) {
        Output.printFileNameError();
        // if the file does exist print the contents of the file
      } else {
        stringToOutput = file.getBody();
        Output.printString(stringToOutput);
      }
    } else {
      // get each file the user wants to print
      for (String eachFile : this.fileNames) {
        // get the file the user wants to read
        File file = (File) currFolder.getFile(eachFile);
        if (file == null) {
          // if the file does not exist print an error message
          // stop running the command
          Output.printFileNameError();
        } else {
          // if the file does exist print the contents of the file
          // print three lines to separate each file being read
          stringToOutput += file.getBody() + "\n\n\n\n";
        }
      }
      Output.printSingleLineString(stringToOutput);
    }
  }

  /**
   * This method return the body of the file as a String to be printed on the
   * console
   * 
   * @return stringToOutput - the body of the file that needs to be printed
   */
  public String getStringToOutput() {
    return stringToOutput;
  }
  
  /**
   * This function return the instructions on how to use the command cat.
   * 
   * @return a string telling users the how the command works
   */

  public String manual() {
    return "cat FILE1 [FILE2  …] - Displays the contents of the specified\n"
        + "files concatenated within the shell.\n";
  }
}
