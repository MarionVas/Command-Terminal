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
   * 
   * @param jFileSystem - The JFileSystem with all the file and folder
   * @param parameter - The string array with all the arguments the user enters
   */

  public EchoOverwrite(JFileSystem manager, String[] parameter) {
    this.fileSystem = manager;
    this.parameter = parameter;
  }

  /**
   * This method will replace the contents of the outfile given in the
   * parameters of the constructor with the String given in the parameter
   */

  public void execute() {
    // find the file that the user is going to be working with
    File replaceFile = findFile();
    // replace the contents of the file with the String
    replace(replaceFile, parameter[0]);
  }

  /**
   * This method will return the outFile given in the parameters of the
   * constructor, if the outfile specified does not exist, it will create a file
   * with the name of the outfile and return the newly created file
   */

  public File findFile() {
    // get the current working directory
    Folder currFolder = fileSystem.getCurrFolder();
    // find the name of the outfile
    String fileName = parameter[2];
    // get the outfile
    File file = currFolder.getFile(fileName);
    // check if the file exists
    if (file == null) {
      // if the file does not exist make a new file
      file = new File(fileName);
      // add the file to the current working directory
      currFolder.addChildren(file);
    }
    // return the file
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
   * This method will replace the body of the file with the string provided in
   * the parameter given to the constructor
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
