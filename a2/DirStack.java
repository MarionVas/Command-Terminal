package a2;

import java.util.Stack;

public class DirStack 
{
  public Stack<String> directoryStack;

  // Constructor for the DirStack
  public DirStack()
  {
    // make the directory stack
    directoryStack = new Stack<String>();
  }
  
  public void pushD(String dir)
  {
    directoryStack.push(dir);
  }
  
  public Stack<String> getStack()
  {
    return directoryStack;
  }
  
  public String popD()
  {
    return directoryStack.pop();
  }
}
