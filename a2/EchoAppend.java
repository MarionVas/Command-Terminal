package a2;

public class EchoAppend extends EchoOverwrite implements CommandInterface {

  /**
   * The constructor uses the constructor from EchoOverwrite
   * 
   * @param jFileSystem - The JFileSystem with all the file and folder
   * @param parameter - The string array with all the arguments the user enters
   */

  public EchoAppend(JFileSystem manager, String[] parameter) {
    // call on the super class' constructor
    super(manager, parameter);
  }

  /**
   * This method will override the parent's execute method so that it adds a
   * line to the end of a file if an file exists instead of replacing the file
   * with the String passed into the parameter
   */

  @Override
  public void execute() {
    // run the super class' fileFile method to get the file to work with
    File appendFile = super.findFile();
    if (super.isFileNull()) {
      Output.printFileNameError();
    } else {
      // add the String to the file instead of replacing the file's content
      append(appendFile, super.getString());
    }
  }

  /**
   * This method will add a given line to the end of the given file
   * 
   * @param file - the outfile given in the parameter
   * @param body - the String given in the parameter
   */

  private void append(File file, String body) {
    file.addToBody(body);
  }


  /**
   * This method returns the instructions on how to use the command echo STRING
   * [>> OUTFILE].
   * 
   * @return a string telling users the how the command works
   */


  public String manual() {
    return "echo STRING >> OUTFILE - modifies the outfile such that it\n"
        + "appends an extra string line “STRING” to the end of the file.\n";

  }
}
