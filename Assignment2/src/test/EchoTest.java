package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class EchoTest {
  private JFileSystem jFileSystem;
  private Echo echo;
  private String[] parameters;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
  }

  // print a string
  @Test
  public void testExecute1() {
    /*
     * test to using Echo to print a string
     * 
     * Expected type of echo command to be called is null.
     * 
     * Expected output string should match the String given in the parameters of
     * the constructor
     */
    parameters = new String[1];
    parameters[0] = "\"This is a test.\"";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), null);
    assertEquals(echo.getStringToOutput(), "This is a test.");
  }

  public void testExecute2() {
    /*
     * test to using Echo to print an empty string
     * 
     * Expected type of echo command to be called is null.
     * 
     * Expected output string should be an empty String
     */
    parameters = new String[1];
    parameters[0] = "\"\"";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), null);
    assertEquals(echo.getStringToOutput(), "");
  }

  @Test
  public void testExecute3() {
    /*
     * test to use Echo to call echoOverwrite class
     * 
     * Expected type of echo command to be called is overwrite.
     */
    parameters = new String[3];
    parameters[0] = "\"test2 has passed.\"";
    parameters[1] = ">";
    parameters[2] = "test2";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), "overwrite");
  }

  @Test
  public void testExecute4() {
    /*
     * test to use Echo to call echoAppend class
     * 
     * Expected type of echo command to be called is append.
     */
    parameters = new String[3];
    parameters[0] = "\"test3 has passed.\"";
    parameters[1] = ">>";
    parameters[2] = "test3";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), "append");
  }

  @Test
  public void testExecute5() {
    /*
     * test to use Echo to call echoOverwrite class with Strings with spaces for
     * String parameter
     * 
     * Expected type of echo command to be called is overwrite.
     */
    parameters = new String[6];
    parameters[0] = "\"test4";
    parameters[1] = "has";
    parameters[2] = "passed.\"";
    parameters[3] = ">";
    parameters[4] = "test4";
    parameters[5] = ".";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), "overwrite");
  }

  @Test
  public void testExecute6() {
    /*
     * test to use Echo to call echoAppend class with Strings with spaces for
     * String parameter
     * 
     * Expected type of echo command to be called is append.
     */
    parameters = new String[6];
    parameters[0] = "\"test4";
    parameters[1] = "has";
    parameters[2] = "passed.\"";
    parameters[3] = ">>";
    parameters[4] = "test4";
    parameters[5] = ".";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), "append");
  }

  @Test
  public void testExecute7() {
    /*
     * test to use Echo to call echoOverwrite class with Strings with spaces for
     * String parameter
     * 
     * Expected type of echo command to be called is overwrite.
     */
    parameters = new String[3];
    parameters[0] = "\"test5 has passed.\"";
    parameters[1] = "&";
    parameters[2] = "test5";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), null);
    assertEquals(echo.getStringToOutput(), "");
  }

  @Test
  public void testExecute8() {
    /*
     * test to use Echo to with invalid symbol as parameter
     * 
     * Expected type of echo command to be called is null.
     * 
     * Expected output string should be an empty String
     */
    parameters = new String[3];
    parameters[0] = "\"test6 has passed.\"";
    parameters[1] = "<";
    parameters[2] = "test6";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), null);
    assertEquals(echo.getStringToOutput(), "");
  }

  @Test
  public void testExecute9() {
    /*
     * test to use Echo to with invalid symbol as parameter
     * 
     * Expected type of echo command to be called is null.
     * 
     * Expected output string should be an empty String
     */
    parameters = new String[3];
    parameters[0] = "\"test7 has passed.\"";
    parameters[1] = "<<";
    parameters[2] = "test7";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), null);
    assertEquals(echo.getStringToOutput(), "");
  }
}
