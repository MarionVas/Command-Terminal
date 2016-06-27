package a2;

public class Exit implements CommandInterface {
  private FileSystem manager;
  
  /**
   * The constructor
   * 
   * @param jFileSystem - The JFileSystem with all the file and folder
   */

  public Exit(JFileSystem jFileSystem) {
    this.manager = jFileSystem;
  }

  /**
   * This method will terminate the console
   */
  
  public void execute() {
    // method to exit consoles
    System.exit(0);
  }

  /**
   * This function return the instructions on how to use the command exit.
   * 
   * @return a string telling users the how the command works
   */

  public String manual() {
    return "exit - Terminates the session/program.\n";
  }

}
