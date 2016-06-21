package a2;

import java.util.Vector;

public class EchoOverwrite implements CommandInterface {
  // collaboration with fileSystem
  private FileSystem fileSystem; // del equals
  // initialize a variable to hold the index of "<"
  private int indexOfSymbol;
  // initialize a variable to hold the parameter passed to the command
  private String parameter;
  // get the current working directory
  private Folder currFolder = fileSystem.getCurrFolder();

  /**
   * The constructor
   */

  public EchoOverwrite(JFileSystem manager, String[] parameter) {
    // fileSystem = FileSystem.createFileSystemInstance();
    this.fileSystem = manager;
    this.parameter = parameter[0];
    // finds the index of the ">" symbol
    this.indexOfSymbol = this.parameter.indexOf(">");
  }

  /**
   * This function will take the parameter passed into the constructor and
   * display the string if no outfile is specified. If an outfile is specified,
   * this function will replace the contents of the outfile with the string
   * given.
   */

  public void execute() {
    // check if an outfile is specified
    if (this.indexOfSymbol == -1) {
      // if an outfile is not specified, display the string
      Output.printString(parameter);
    } else {
      // if an outfile is specified, get the string portion
      String body = parameter.substring(0, indexOfSymbol - 1);
      // find the name of the outfile
      String fileName = findFileName();
      // get the outfile
      File file = currFolder.getFile(fileName);
      // check if the file exists
      if (file.equals(null)) {
        // if the file does not exist display an error
        File newFile = new File(fileName);
        currFolder.addChildren(newFile);
      } else {
        // if the file exists, replace the body of the file with the string
        replace(file, body);
      }
    }
  }

  /**
   * This function will return the name of the outfile given the parameter given
   * to the constructor
   * 
   * @return fileName - The name of the outfile
   */

  private String findFileName() {
    // find the name of the outfile
    String fileName = this.parameter.substring(this.indexOfSymbol + 3,
        this.parameter.length());
    return fileName;
  }

  /**
   * This function will replace the body of the file with the string provided in
   * the parameter
   */

  private void replace(File file, String body) {
    file.setBody(body);
  }

  /**
   * This function return the index of the symbol ">".
   * 
   * @return indexOfSymbol - The index of the symbol ">" provided in the
   *         constructor
   */

  public int getIndexOfSymbol() {
    return indexOfSymbol;
  }

  /**
   * This function return the parameter provided to the constructor
   * 
   * @return parameter - The parameter provided to the constructor
   */

  public String getParameter() {
    return parameter;
  }

  /**
   * This function return the instructions on how to use the command echo 
   * String [> OUTFILE].
   * 
   * @return a string telling users the how the command works
   */
  
  public String man(){
    return "Print a string on the shell. If a “> OUTFILE” parameter is\n"
        + "specified within the command, modify the file such that it only\n" 
        + "contains the given string argument “STRING”. If the file does \n"
        + "not exist, create a new file with the given string argument as the\n" 
        + "body. i.e. echo STRING > OUTFILE";
  }

}
