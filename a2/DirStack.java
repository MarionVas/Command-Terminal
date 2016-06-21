package a2;

import java.util.Stack;

public class DirStack 
{
  public Stack<String> DirectoryStack;
  private JFileSystem fileSystem;

  // Constructor for the DirStack
  public DirStack(JFileSystem fileSystem)
  {
    // make the directory stack
    DirectoryStack = new Stack<String>();
    this.fileSystem = fileSystem;
  }
  
  public void pushD(String dir)
  {
    DirectoryStack.push(dir);
    CD location = new CD(fileSystem, dir);
    location.execute();
  }
  
  public void popD()
  {
    String filePath = (String) DirectoryStack.pop();
    CD location = new CD(fileSystem, filePath);
    location.execute();
    
  }
}
