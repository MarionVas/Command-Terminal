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
  
    

    

  public void execute() {
    // push the location into the temporary directoryStack
    directoryStack.pushD(location[0]);
    // send the new directory stack to the FileSystem
    Manager.setDirStack(directoryStack);
    // change the current directory to the given location
    CD changeDirectory = new CD(Manager, location);
    changeDirectory.execute();
    
  }

}
