package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import a2.*;

public class PushDTest {
  JFileSystem jFileSystem;
  PushD pushD;
  String[] location;
  DirStack dirStack;

  @Before
  public void setUp() throws Exception 
  {
    jFileSystem = new JFileSystem();
    jFileSystem.setFullPath("/a/b/c");
    location = new String[1];
    dirStack = new DirStack();
  }

  @Test
  public void testExecute() 
  {
    location[0] = "/a/b/c";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    dirStack.pushD("/a/b/c");
    assertEquals(dirStack, jFileSystem.getDirStack());
    
  }

}
