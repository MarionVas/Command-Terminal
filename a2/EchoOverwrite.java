package a2;

import java.util.Vector;

public class EchoOverwrite implements CommandInterface {
  // collaboration with fileSystem
  private FileSystem fileSystem; // del equals
  // initialize a variable to hold the index of ">"
  private int indexOfSymbol;
  // initialize a variable to hold the parameter passed to the command
  private String[] parameter;

  /**
   * The constructor
   */

  public EchoOverwrite(JFileSystem manager, String[] parameter) {
    // fileSystem = FileSystem.createFileSystemInstance();
    this.fileSystem = manager;
    this.parameter = parameter;
  }

  /**
   * This function will take the parameter passed into the constructor and
   * display the string if no outfile is specified. If an outfile is specified,
   * this function will replace the contents of the outfile with the string
   * given.
   */

  public void execute() {
    // check if an outfile is specified
    if (parameter.length == 1) {
      // if an outfile is not specified, display the string
      Output.printString(parameter[0]);
    } else {
      File replaceFile = findFile();
      replace(replaceFile, parameter[0]);
    }
  }

  public File findFile() {
    // get the current working directory
    Folder currFolder = fileSystem.getCurrFolder();
    // find the name of the outfile
    String fileName = parameter[2];
    // get the outfile
    File file = currFolder.getFile(fileName);
    // check if the file exists
    if (file == null) {
      // if the file does not exist display an error
      file = new File(fileName);
      currFolder.addChildren(file);
      // if the file exists, replace the body of the file with the string
    }
    return file;

  }

  /*
   * /** This function will return the name of the outfile given the parameter
   * given to the constructor
   * 
   * @return fileName - The name of the outfile
   * 
   * 
   * private String findFileName() { // find the name of the outfile String
   * fileName = this.parameter.substring(this.indexOfSymbol + 2,
   * this.parameter.length()); return fileName; }
   */


  /**
   * This function will replace the body of the file with the string provided in
   * the parameter
   */

  private void replace(File file, String body) {
    file.setBody(body);
  }

  /*
   * /** This function return the index of the symbol ">".
   * 
   * @return indexOfSymbol - The index of the symbol ">" provided in the
   * constructor
   * 
   * 
   * public int getIndexOfSymbol() { return indexOfSymbol; }
   */



  /**
   * This function return the parameter provided to the constructor
   * 
   * @return parameter - The parameter provided to the constructor
   */

  public String[] getParameter() {
    return parameter;
  }

  /**
   * This function return the instructions on how to use the command echo String
   * [> OUTFILE].
   * 
   * @return a string telling users the how the command works
   */

  public String man() {
    return "Print a string on the shell. If a “> OUTFILE” parameter is\n"
        + "specified within the command, modify the file such that it only\n"
        + "contains the given string argument “STRING”. If the file does \n"
        + "not exist, create a new file with the given string argument as the\n"
        + "body. i.e. echo STRING > OUTFILE";
  }

}
