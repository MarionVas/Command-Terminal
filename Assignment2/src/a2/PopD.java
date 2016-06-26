package a2;

public class PopD implements CommandInterface {
  private JFileSystem Manager;
  private DirStack directoryStack;
  private String[] location;

  // basic constructor for the PushD class
  public PopD(JFileSystem fileManager) {
    this.Manager = fileManager;
    this.directoryStack = Manager.getDirStack();
    this.location = new String[1];
  }

  public void execute() {
    /*
     * Get the last input location from the DirStack as per LIFO save this in a
     * string array to allow for the same type argument to be placed in location
     */
    try {
      // pop the last saved location from the DirStack
      location[0] = directoryStack.popD();
      // updated the FileSystem DirStack object
      Manager.setDirStack(directoryStack);
      // change the current directory to the given location
      CD changeDirectory = new CD(Manager, location);
      changeDirectory.execute();
    } catch (Exception e) {
      Output.printDirectoryStackError();
    }
  }

  public String manual() {
    return "popd - Removes the top directory on the directory stack and makes\n"
        + "it the current working directory. If no directory exists, an error\n"
        + "message is returned.\n";
  }
}
