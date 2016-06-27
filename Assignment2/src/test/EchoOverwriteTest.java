package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class EchoOverwriteTest {
  private JFileSystem jFileSystem;
  private EchoOverwrite echo1;
  private EchoOverwrite echo2;
  private File file1;
  private String[] parameter1 = new String[3];
  private String[] parameter2 = new String[3];

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    file1 = new File("test1");
    file1.setBody("test1 has failed.");
    rootFolder.addChildren(file1);
  }

  @Test
  public void testExecute1() {
    /*
     * test to use echo to replace the contents of an existing file with the
     * String passed into the echo command
     * 
     * Expected body of the file is the String passed into echo command
     * 
     */
    parameter1[0] = "\"test1 has passed.\"";
    parameter1[1] = ">";
    parameter1[2] = "test1";
    echo1 = new EchoOverwrite(jFileSystem, parameter1);
    echo1.execute();
    Folder currFolder = jFileSystem.getCurrFolder();
    File editedFile = currFolder.getFile("test1");
    assertEquals(editedFile.getBody(), "test1 has passed.");
  }

  // creating a new file
  @Test
  public void testExecute2() {
    /*
     * test to use echo to create a new file with the string as the contents
     * 
     * Expected body of the file is the String passed into echo command
     * 
     */
    parameter1[0] = "\"test2 has passed.\"";
    parameter1[1] = ">";
    parameter1[2] = "test2";
    echo1 = new EchoOverwrite(jFileSystem, parameter1);
    echo1.execute();
    Folder currFolder = jFileSystem.getCurrFolder();
    File editedFile = currFolder.getFile("test2");
    assertEquals(editedFile.getBody(), "test2 has passed.");
  }

  @Test
  public void testExecute3() {
    /*
     * test to use echo to create a new file with String as the contents. use
     * another echo to overwrite the existing file's with the second echo's
     * String.
     * 
     * Expected body of the file is the String passed into the second echo
     * command
     * 
     */
    parameter1[0] = "\"test3 has been created and test3 has failed.\"";
    parameter1[1] = ">";
    parameter1[2] = "test3";
    echo1 = new EchoOverwrite(jFileSystem, parameter1);
    echo1.execute();
    parameter2[0] = "\"test3 has been created and test3 has passed.\"";
    parameter2[1] = ">";
    parameter2[2] = "test3";
    echo2 = new EchoOverwrite(jFileSystem, parameter2);
    echo2.execute();
    Folder currFolder = jFileSystem.getCurrFolder();
    File editedFile = currFolder.getFile("test3");
    assertEquals(editedFile.getBody(),
        "test3 has been created and test3 has passed.");
  }

  @Test
  public void testExecute4() {
    /*
     * test to use echo to create a new file with an invalid OUTFILE name
     * 
     * Expected the OUTFILE after echo has executed is null
     * 
     */
    parameter1[0] = "\"test4 has been created.\"";
    parameter1[1] = ">";
    parameter1[2] = "test 4";
    echo1 = new EchoOverwrite(jFileSystem, parameter1);
    echo1.execute();
    assertTrue(echo1.isFileNull());
  }

  @Test
  public void testExecute5() {
    /*
     * use echo to create an new file with an empty String as the contents
     * 
     * Expected body of the file is an empty String passed into echo command
     * 
     */
    parameter1[0] = "\"\"";
    parameter1[1] = ">";
    parameter1[2] = "test5";
    echo1 = new EchoOverwrite(jFileSystem, parameter1);
    echo1.execute();
    Folder currFolder = jFileSystem.getCurrFolder();
    File editedFile = currFolder.getFile("test5");
    assertEquals(editedFile.getBody(), "");
  }

  @Test
  public void testExecute6() {
    /*
     * test to use echo to replace the contents of an existing file with an
     * empty String
     * 
     * Expected body of the file is an empty String passed into echo command
     * 
     */
    parameter1[0] = "\"\"";
    parameter1[1] = ">";
    parameter1[2] = "test1";
    echo1 = new EchoOverwrite(jFileSystem, parameter1);
    echo1.execute();
    Folder currFolder = jFileSystem.getCurrFolder();
    File editedFile = currFolder.getFile("test1");
    assertEquals(editedFile.getBody(), "");
  }
}
