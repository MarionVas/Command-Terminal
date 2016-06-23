package a2;

public class PushD implements CommandInterface
{
  private JFileSystem Manager;
  private DirStack directoryStack;
  private String[] location;
  
  // basic constructor for the PushD class
  public PushD(JFileSystem fileManager, DirStack directoryStack, String[] location)
  {
    this.Manager = fileManager;
    this.directoryStack = directoryStack;
    this.location = location;
  }
  
    

    

  public void execute() {
    // push the location into the directoryStack
    directoryStack.pushD(location[0]);
    // change the current directory to the given location
    CD changeDirectory = new CD(Manager, location);
    changeDirectory.execute();
    
  }

}
