package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;


public class CatTest {
  private JFileSystem jFileSystem;
  private Cat cat;
  private File file1;
  private File file2;
  private File file3;
  private String[] fileNames;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    file1 = new File("test1");
    rootFolder.addChildren(file1);
    file2 = new File("test2");
    file2.setBody("This is a test in test2.");
    rootFolder.addChildren(file2);
    file3 = new File("test3");
    file3.setBody("This is a test in test3,\nAnd it works.");
    rootFolder.addChildren(file3);
  }

  @Test
  public void testExecute1() {
    fileNames = new String[1];
    fileNames[0] = "test1";
    cat = new Cat(jFileSystem, fileNames);
    cat.execute();
    assertEquals(cat.getStringToOutput(), "");
  }

  @Test
  public void testExecute2() {
    fileNames = new String[1];
    fileNames[0] = "test2";
    cat = new Cat(jFileSystem, fileNames);
    cat.execute();
    assertEquals(cat.getStringToOutput(), "This is a test in test2.");
  }

  @Test
  public void testExecute3() {
    fileNames = new String[1];
    fileNames[0] = "test3";
    cat = new Cat(jFileSystem, fileNames);
    cat.execute();
    assertEquals(cat.getStringToOutput(),
        "This is a test in test3,\nAnd it works.");
  }

  @Test
  public void testExecute4() {
    fileNames = new String[1];
    fileNames[0] = "test4";
    cat = new Cat(jFileSystem, fileNames);
    cat.execute();
    assertEquals(cat.getStringToOutput(), "");
  }

  @Test
  public void testExecute5() {
    fileNames = new String[2];
    fileNames[0] = "test2";
    fileNames[1] = "test3";
    cat = new Cat(jFileSystem, fileNames);
    cat.execute();
    assertEquals(cat.getStringToOutput(),
        "This is a test in test2.\n\n\n\nThis is a test in test3,"
        + "\nAnd it works.\n\n\n\n");
  }
  
  @Test
  public void testExecute6() {
    fileNames = new String[3];
    fileNames[0] = "test2";
    fileNames[1] = "test4";
    fileNames[2] = "test3";
    cat = new Cat(jFileSystem, fileNames);
    cat.execute();
    assertEquals(cat.getStringToOutput(),
        "This is a test in test2.\n\n\n\nThis is a test in test3,"
        + "\nAnd it works.\n\n\n\n");
  }
  
  @Test
  public void testExecute7() {
    fileNames = new String[4];
    fileNames[0] = "test1";
    fileNames[1] = "test2";
    fileNames[2] = "test4";
    fileNames[3] = "test3";
    cat = new Cat(jFileSystem, fileNames);
    cat.execute();
    assertEquals(cat.getStringToOutput(),
        "\n\n\n\nThis is a test in test2.\n\n\n\nThis is a test in test3,"
        + "\nAnd it works.\n\n\n\n");
  }
}
