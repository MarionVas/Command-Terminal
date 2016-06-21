package a2;

public class EchoAppend extends EchoOverwrite implements CommandInterface {

  /**
   * The constructor uses the constructor from EchoOverwrite
   */
  public EchoAppend(JFileSystem manager, String parameter) {
    super(manager, parameter);
  }

  /**
   * This function will return the name of the outfile given the parameter given
   * to the constructor
   * 
   * @return fileName - The name of the outfile
   */

  private String findFileName() {
    String fileName = getParameter().substring(getIndexOfSymbol() + 2,
        getParameter().length());
    return fileName;
  }

  /**
   * This function will add string provided in the parameter to the body of the
   * outfile
   */

  private void replace(File file, String body) {
    file.addToBody(body);
  }
  
  /**
   * This function return the instructions on how to use the command echo 
   * STRING [>> OUTFILE].
   * 
   * @return a string telling users the how the command works
   */

  public String man() {
    return "modifies the outfile such that it appends an extra string\n"
        + "line “STRING” to the end of the file. i.e. echo STRING >> OUTFILE";
  }
}
