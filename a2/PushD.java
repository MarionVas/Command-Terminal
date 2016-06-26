package a2;

public class PushD implements CommandInterface
{
  private JFileSystem Manager;
  private DirStack directoryStack;
  private String[] location;
  
  // basic constructor for the PushD class
  public PushD(JFileSystem fileManager, String[] location)
  {
    this.Manager = fileManager;
    this.directoryStack = fileManager.getDirStack();
    this.location = location;
  }
  
  /**
   * The purpose of this method is to do the PushD command, which enters
   * a given string into a DirStack and changes the directory to the given
   * string path
   */
  public void execute() {
    // push the location into the temporary directoryStack
    directoryStack.pushD(location[0]);
    // send the new directory stack to the FileSystem
    Manager.setDirStack(directoryStack);
    // change the current directory to the given location
    CD changeDirectory = new CD(Manager, location);
    changeDirectory.execute();
    
  }
  
  /**
   * The purpose of this method is display the manual for the class - used
   * for the Man class
   */
  public String manual() {
    return "pushd DIR - Saves the old current working directory in the\n"
        + "directory stack so that it can be returned at a later time using\n"
        + "the 'popd' command. The specified DIR becomes the new current\n"
        + "working directory.\n";     
  }
}
