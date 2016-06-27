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
    parameters = new String[1];
    parameters[0] = "\"This is a test.\"";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), null);
  }

  // overwrite
  @Test
  public void testExecute2() {
    parameters = new String[3];
    parameters[0] = "\"test2 has passed.\"";
    parameters[1] = ">";
    parameters[2] = "test2";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), "overwrite");
  }

  // overwrite
  @Test
  public void testExecute3() {
    parameters = new String[3];
    parameters[0] = "\"test3 has passed.\"";
    parameters[1] = ">>";
    parameters[2] = "test3";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), "append");
  }

  // invalid params (len > 1 and [1] =/= > || >>)
  // this is fine, fixes params so that [1] == > || >>
  @Test
  public void testExecute4() {
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
  public void testExecute5() {
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
  public void testExecute6() {
    parameters = new String[3];
    parameters[0] = "\"test5 has passed.\"";
    parameters[1] = "&";
    parameters[2] = "test5";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), null);
  }

  @Test
  public void testExecute7() {
    parameters = new String[3];
    parameters[0] = "\"test6 has passed.\"";
    parameters[1] = "<";
    parameters[2] = "test6";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), null);
  }

  @Test
  public void testExecute8() {
    parameters = new String[3];
    parameters[0] = "\"test7 has passed.\"";
    parameters[1] = "<<";
    parameters[2] = "test7";
    echo = new Echo(jFileSystem, parameters);
    echo.execute();
    assertEquals(echo.getEchoType(), null);
  }
}
