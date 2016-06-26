package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.CD;
import a2.DirStack;
import a2.File;
import a2.Folder;
import a2.JFileSystem;
import a2.Mkdir;

public class JFileSystemTest {
  private JFileSystem jFileSystem;
  private String[] fileName;
  private String[] fileNames;
  private Mkdir mkdir1;
  private CD cd;
  private Folder newFolder;
  private Folder rootFolder;
  private DirStack testStack;
  
  
  @Before
  public void setUp() throws Exception 
  {
    this.jFileSystem = new JFileSystem();
    this.rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
    newFolder = new Folder("Test", "/Test");
    fileName = new String[1];
    fileNames = new String[3];
    this.testStack = new DirStack();
  }
  @Test
  public void testgetCurrPath() 
  {
    fileName[0] = "a";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }
  @Test
  public void testgetObject() 
  {
    fileNames = new String[5];
    fileNames[0] = "a";
    fileNames[1] = "a1";
    fileNames[2] = "a2";
    fileNames[3] = "/a/Test1";
    fileNames[4] = "a1/Test2";
    mkdir1 = new Mkdir(jFileSystem, fileNames);
    mkdir1.execute();
    assertEquals("/a1/Test2", ((Folder) jFileSystem.getObject("/a1/Test2")).getPath());
  }
  @Test
  public void testgetObject2() 
  {
    fileNames = new String[5];
    fileNames[0] = "a";
    fileNames[1] = "a1";
    fileNames[2] = "a2";
    fileNames[3] = "/a/Test1";
    fileNames[4] = "a1/Test2";
    mkdir1 = new Mkdir(jFileSystem, fileNames);
    mkdir1.execute();
    assertEquals("a", ((Folder) jFileSystem.getObject("a")).getName());
  }
  @Test
  public void testSetFullPath() 
  {
    jFileSystem.setFullPath("/testPath");
    assertEquals("/testPath", jFileSystem.getCurrPath());
  }
  @Test
  public void testadd() 
  {
    jFileSystem.add(newFolder);
    assertEquals("/Test", jFileSystem.getFullPaths().get(1));
  }
  @Test
  public void testaddFullPath() 
  {
    jFileSystem.addFullPath(newFolder.getPath());
    assertEquals("/Test", jFileSystem.getFullPaths().get(1));
  }
  @Test
  public void testCheckValidPath() 
  {
    jFileSystem.addFullPath(newFolder.getPath());
    assertTrue(jFileSystem.checkValidPath("/Test"));
  }
  @Test
  public void testSetCurrFolder() 
  {
    jFileSystem.setCurrFolder(newFolder);
    assertEquals("/Test", jFileSystem.getCurrFolder().getPath());
  }
  @Test
  public void testsetRootFolder() 
  {
    // Was set in the set up
    assertEquals(this.rootFolder, jFileSystem.getRootFolder());
  }
  @Test
  public void testgetRootFolder() 
  {
    assertEquals(this.rootFolder, jFileSystem.getRootFolder());
  }
  @Test
  public void testsetDirStack() 
  {
    
    jFileSystem.setDirStack(testStack);
    assertEquals(testStack, jFileSystem.getDirStack());
  }
  @Test
  public void testgetDirStack() 
  {
    DirStack newTestStack = new DirStack();
    jFileSystem.setDirStack(newTestStack);
    assertEquals(newTestStack, jFileSystem.getDirStack());
  }
}
