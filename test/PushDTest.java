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
  Mkdir mkdir1;
  Mkdir mkdir2;
  Mkdir mkdir3;

  @Before
  public void setUp() throws Exception 
  {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
    String[] path = {"a", "b", "c"};
    String[] path2 = {"/a/a1"};
    String[] path3 = {"/a/a1/a2"};
    mkdir1 = new Mkdir(jFileSystem, path);
    mkdir1.execute();
    mkdir2 = new Mkdir(jFileSystem, path2);
    mkdir2.execute();
    mkdir3 = new Mkdir(jFileSystem, path3);
    mkdir3.execute();

    location = new String[1];
    dirStack = jFileSystem.getDirStack();
  }

  @Test
  public void testExecute() 
  {
    location[0] = "/a/a1/a2";
    pushD = new PushD(jFileSystem, location);
    pushD.execute();
    dirStack.pushD("/a/a1/a2");
    assertEquals(dirStack, jFileSystem.getDirStack());
    
  }

}
