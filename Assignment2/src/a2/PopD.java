package a2;

public class PopD 
{
  private JFileSystem Manager;
  private DirStack directoryStack;
  private String[] location;
  
  // basic constructor for the PushD class
  public PopD(JFileSystem fileManager, DirStack directoryStack)
  {
    this.Manager = fileManager;
    this.directoryStack = directoryStack;
  }

  public void execute() 
  {
    /* Get the previously input location from the DirStack as per LIFO
     * save this in a string array to allow for the same type argument
     * to be placed in location
     */
    location[0] = directoryStack.popD();
    // change the current directory to the given location
    CD changeDirectory = new CD(Manager, location);
    changeDirectory.execute();
    
  }

}
