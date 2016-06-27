package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class EchoAppendTest {
  private JFileSystem jFileSystem;
  private EchoAppend echo1;
  private EchoAppend echo2;
  private EchoAppend echo3;
  private File file1;
  private String[] parameter = new String[3];

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    file1 = new File("test1");
    file1.setBody("test1 has ");
    rootFolder.addChildren(file1);
  }

  @Test
  public void testExecute1() {
    parameter[0] = "passed.";
    parameter[1] = ">>";
    parameter[2] = "test1";
    echo1 = new EchoAppend(jFileSystem, parameter);
    echo1.execute();
    File editedFile = (File) jFileSystem.getObject("/test1");
    assertEquals(editedFile.getBody(), "test1 has passed.");
  }
  // add to existing
  // create new
  // isFileNull()

}
