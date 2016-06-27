package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class FileTest {

  private JFileSystem jFileSystem;
  private String body;
  private File file;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    body = "";
    file = new File("testFile");
  }

  // add nothing to body
  @Test
  public void testAddToBody1() {
    file.addToBody(body);
    assertEquals(file.getBody(), "");
  }

  @Test
  public void testAddToBody2() {
    file.addToBody("test");
    assertEquals(file.getBody(), "test");
  }

  @Test
  public void testAddToBody3() {
    file.addToBody("testing testing ");
    file.addToBody("123");
    assertEquals(file.getBody(), "testing testing 123");
  }
  
  @Test
  public void testGetName() {
    assertEquals(file.getName(), "testFile");
  }

  // set body to nothing
  @Test
  public void testSetBody1() {
    file.setBody(body);
    assertEquals(file.getBody(), "");
  }

  @Test
  public void testSetBody2() {
    file.setBody("test");
    assertEquals(file.getBody(), "test");
  }

  @Test
  public void testSetBody3() {
    file.setBody("testing testing ");
    file.setBody("123");
    assertEquals(file.getBody(), "123");
  }

}
