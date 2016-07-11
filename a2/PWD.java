package a2;

public class PWD extends OutputToFile implements CommandInterface {
  private JFileSystem fileSystem;
  private String symbol = null;
  private String outfile;
  private String stringToOutput;

  /**
   * The constructor
   * 
   * @param manager - The JFileSystem with all the file and folder
   */

  public PWD(JFileSystem manager) {
    this.fileSystem = manager;
  }

  /**
   * The constructor with string array as parameters
   * 
   * @param manager - The JFileSystem with all the file and folder
   * @param parameter - The string array with all the arguments the user enters
   */

  public PWD(JFileSystem manager, String[] parameters) {
    this.fileSystem = manager;
    this.symbol = parameters[0];
    this.outfile = parameters[1];
  }

  /**
   * This method gets the current working path of in the filesystem and prints
   * it on the console. If the command is redirected with "> OUTFILE", this
   * method will redirect the output to the OUTFILE. If the command is
   * redirected with ">> OUTFILE", this method will add the output to the body
   * of the OUTFILE.
   */

  public void execute() {
    // get the path of the working directory as a string
    stringToOutput = fileSystem.getCurrPath();
    // if there is no redirection, print the string
    if (this.symbol == null) {
      Output.printString(stringToOutput);
      // if the output is redirected with "> OUTFILE" replace the OUTFILE's
      // contents with the command's output
    } else if (symbol.equals(">")) {
      overwrite(fileSystem, stringToOutput, outfile);
      // if the output is redirected with ">> OUTFILE" add the command's output
      // to the body of the OUTFILE
    } else if (symbol.equals(">>")) {
      append(fileSystem, stringToOutput, outfile);
    }
  }

  /**
   * This function returns the instructions on how to use the command pwd.
   * 
   * @return a string telling users the how the command works
   */
  public String manual() {
    return "pwd - Prints the current working directory with its whole path.\n";
  }

  /**
   * This method returns the body of the file as a String to be printed on the
   * console
   * 
   * @return stringToOutput - the body of the file that needs to be printed
   */
  public String getStringToOutput() {
    return stringToOutput;
  }
}
