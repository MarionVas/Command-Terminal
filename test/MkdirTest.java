package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class MkdirTest 
{
  private JFileSystem jFileSystem;
  private String[] fileName;
  private String[] fileNames;
  private Mkdir mkdir1;
  private CD cd;

  @Before
  public void setUp() throws Exception 
  {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    fileName = new String[1];
    fileNames = new String[3];
  }

  @Test
  public void testExecute1() 
  {
    fileName[0] = "a";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertTrue(jFileSystem.checkValidPath("/a"));
  }
  
  @Test
  public void testExecute2() 
  {
    fileNames[0] = "a";
    fileNames[1] = "a1";
    fileNames[2] = "a2";
    mkdir1 = new Mkdir(jFileSystem, fileNames);
    mkdir1.execute();
    assertTrue(jFileSystem.checkValidPath("/a") &&
        jFileSystem.checkValidPath("/a1") && jFileSystem.checkValidPath("/a2"));
  }
  
  @Test
  public void testExecute3() 
  {
    fileName[0] = "a";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    fileName[0] = "/a/a1";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    fileName[0] = "/a/a1/a2";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertTrue(jFileSystem.checkValidPath("/a/a1/a2"));
  }
  
  @Test
  public void testExecute4() 
  {
    fileName[0] = "a****dsda";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertFalse(jFileSystem.checkValidPath("a****dsda"));
  }
  
  @Test
  public void testExecute5() 
  {
    fileName[0] = "a_as&%dsda";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertFalse(jFileSystem.checkValidPath("a_as&%dsda"));
  }
  
  @Test
  public void testExecute6() 
  {
    fileName[0] = "a__________dsda";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertFalse(jFileSystem.checkValidPath("a__________dsda"));
  }
  
  @Test
  public void testExecute7() 
  {
    fileName[0] = "a";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    cd = new CD(jFileSystem, fileName);
    cd.execute();
    fileName[0] = "a1";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertTrue(jFileSystem.checkValidPath("/a/a1"));
  }
  
  public void testExecute8() 
  {
    fileName[0] = "a";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    fileName[0] = "a/a1";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    fileName[0] = "/a/a1";
    cd = new CD(jFileSystem, fileName);
    cd.execute();
    fileName[0] = "a2";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertTrue(jFileSystem.checkValidPath("/a/a1/a2"));
  }
  
  
  

}
