package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import a2.*;

public class PushDTest {
  JFileSystem jFileSystem;
  PushD pushD;
  String[] location;

  @Before
  public void setUp() throws Exception 
  {
    jFileSystem = new JFileSystem();
    jFileSystem.setFullPath("/a/b/c");
    location = new String[1];
  }

  @Test
  public void testExecute() 
  {
    location[0] = "/a/b/c";
    pushD = new PushD(jFileSystem, location);
    
  }

}
