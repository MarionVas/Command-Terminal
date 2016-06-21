package a2;

import java.util.Stack;

public class DirStack 
{
  public Stack<String> DirectoryStack;

  // Constructor for the DirStack
  public DirStack()
  {
    // make the directory stack
    DirectoryStack = new Stack<String>();
  }
  
  public void pushD(String dir)
  {
    DirectoryStack.push(dir);
    CD location = new CD(null, dir);
    location.execute();
  }
  
  public void popD()
  {
    String filePath = (String) DirectoryStack.pop();
    CD location = new CD(null, filePath);
    location.execute();
    
  }
}
