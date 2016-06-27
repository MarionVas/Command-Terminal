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
  public void setUp() throws Exception {
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
  public void testgetCurrPath() {
    /*
     * Testing if proper path is returned
     */
    fileName[0] = "a";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }

  @Test
  public void testgetObject() {
    /*
     * Testing if proper object (either File or Folder or null) is returned
     */
    fileNames = new String[5];
    fileNames[0] = "a";
    fileNames[1] = "a1";
    fileNames[2] = "a2";
    fileNames[3] = "/a/Test1";
    fileNames[4] = "a1/Test2";
    mkdir1 = new Mkdir(jFileSystem, fileNames);
    mkdir1.execute();
    assertEquals("/a1/Test2",
        ((Folder) jFileSystem.getObject("/a1/Test2")).getPath());
  }

  @Test
  public void testgetObject2() {
    /*
     * Testing another case for get object with a LOCAL name instead
     */
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
  public void testgetObject4() {
    /*
     * Testing another case for get object with a FAIL case
     */
    fileNames = new String[5];
    fileNames[0] = "a";
    fileNames[1] = "a1";
    fileNames[2] = "a2";
    fileNames[3] = "/a/Test1";
    fileNames[4] = "a1/Test2";
    mkdir1 = new Mkdir(jFileSystem, fileNames);
    mkdir1.execute();
    assertEquals(null, ((Folder) jFileSystem.getObject("FAIL_CASE")));
  }

  @Test
  public void testSetFullPath() {
    /*
     * Seeing if the path is update properly
     */
    jFileSystem.setFullPath("/testPath");
    assertEquals("/testPath", jFileSystem.getCurrPath());
  }

  @Test
  public void testadd() {
    /*
     * Seeing if the folder is added properly
     */
    jFileSystem.add(newFolder);
    assertEquals("/Test", jFileSystem.getFullPaths().get(1));
  }

  @Test
  public void testaddFullPath() {
    /*
     * Testing if the path is added properly
     */
    jFileSystem.addFullPath(newFolder.getPath());
    assertEquals("/Test", jFileSystem.getFullPaths().get(1));
  }

  @Test
  public void testCheckValidPath() {
    /*
     * Seeing if it can confirm a valid path exists
     */
    jFileSystem.addFullPath(newFolder.getPath());
    assertTrue(jFileSystem.checkValidPath("/Test"));
  }

  @Test
  public void testCheckValidPath1() {
    /*
     * Seeing if it can confirm a path does not exists
     */
    jFileSystem.addFullPath(newFolder.getPath());
    assertFalse(jFileSystem.checkValidPath("/FAIL_CASE"));
  }

  @Test
  public void testSetCurrFolder() {
    /*
     * Seeing if the curr folder is set properly
     */
    jFileSystem.setCurrFolder(newFolder);
    assertEquals("/Test", jFileSystem.getCurrFolder().getPath());
  }

  @Test
  public void testsetRootFolder() {
    // Was set in the set up
    /*
     * Determining if root was set up properly
     */
    assertEquals(this.rootFolder, jFileSystem.getRootFolder());
  }

  @Test
  public void testgetRootFolder() {
    /*
     * Seeing if the root folder can be properly retrieved
     */
    assertEquals(this.rootFolder, jFileSystem.getRootFolder());
  }

  @Test
  public void testsetDirStack() {
    /*
     * Testing if the dirstack can be properly set
     */
    jFileSystem.setDirStack(testStack);
    assertEquals(testStack, jFileSystem.getDirStack());
  }

  @Test
  public void testgetDirStack() {
    /*
     * Testing whether the current dirstack can be retrieved
     */
    DirStack newTestStack = new DirStack();
    jFileSystem.setDirStack(newTestStack);
    assertEquals(newTestStack, jFileSystem.getDirStack());
  }
}
