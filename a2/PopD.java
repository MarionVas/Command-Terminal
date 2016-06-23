package a2;

public class PopD implements CommandInterface
{
  private JFileSystem Manager;
  private DirStack directoryStack;
  private String[] location;
  
  // basic constructor for the PushD class
  public PopD(JFileSystem fileManager)
  {
    this.Manager = fileManager;
    this.directoryStack = Manager.getDirStack();
  }

  public void execute() 
  {
    /* Get the previously input location from the DirStack as per LIFO
     * save this in a string array to allow for the same type argument
     * to be placed in location
     */
    location[0] = directoryStack.popD();
    // updated the FileSystem DirStack object
    Manager.setDirStack(directoryStack);
    // change the current directory to the given location
    CD changeDirectory = new CD(Manager, location);
    changeDirectory.execute();
    
  }

}
