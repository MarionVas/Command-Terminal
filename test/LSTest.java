package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.CD;
import a2.Folder;
import a2.JFileSystem;
import a2.Mkdir;
import a2.LS;

public class LSTest {
  private JFileSystem jFileSystem;
  private String[] fileArg;
  private String[] fileArgs;
  private Mkdir mkdir1;
  private CD cd;
  private LS ls;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    fileArg = new String[1];
    this.fileArgs = new String[3];
    this.ls = new LS(jFileSystem);
    String[] a = new String[2];
    a[0] = "a";
    a[1] = "b";
    Mkdir mkdir = new Mkdir(jFileSystem, a);
    a = new String[1];
    a[0] = "a";
    CD cd = new CD(jFileSystem, a);
    mkdir.execute();
    cd.execute();

    a = new String[3];
    a[0] = "e";
    a[1] = "f";
    a[2] = "q";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();
    a = new String[1];
    a[0] = "e";
    cd = new CD(jFileSystem, a);
    cd.execute();
    a = new String[3];
    a[0] = "x";
    a[1] = "z";
    a[2] = "v";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();
    a = new String[1];
    a[0] = "/a/e/v/TestCase";
    mkdir = new Mkdir(jFileSystem, a);
    mkdir.execute();
    a = new String[1];
    a[0] = "x";
    cd = new CD(jFileSystem, a);
    cd.execute();
  }

  @Test
  public void testExecute1() {
    this.ls = new LS(jFileSystem);
    ls.execute();
    assertEquals("\n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecute2() {
    this.fileArg[0] = "..";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals("/a/e:      v     x     z\n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecute3() {
    this.fileArg[0] = "../../e/";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals("/a/e:      v     x     z\n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecute4() {
    this.fileArg[0] = ".././../e/";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals("/a/e:      v     x     z\n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecute5() {
    this.fileArg[0] = ".././../e/../../b";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals("/b: \n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecute6() {
    this.fileArg[0] = "/a/e/";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals("/a/e:      v     x     z\n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecute7() {
    String[] a = new String[1];
    a[0] = "/a";
    cd = new CD(jFileSystem, a);
    cd.execute();
    this.fileArg[0] = "e/";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals("/a/e:      v     x     z\n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecute8() {
    this.fileArg[0] = "FAIL_ARG";
    this.ls = new LS(jFileSystem, this.fileArg);
    ls.execute();
    assertEquals("That was not a valid path.\n", this.ls.getStringToOutput());
  }

  @Test
  public void testExecute9() {
    this.fileArgs[0] = "/a/e/";
    this.fileArgs[1] = "/";
    this.fileArgs[2] = "/a/e/v/";
    this.ls = new LS(jFileSystem, this.fileArgs);
    ls.execute();
    boolean Equal = this.ls.getStringToOutput().equals(
        "/a/e:      v     x     z\n/:      a     b\n/a/e/v:      " + 
        "TestCase\n");
    assertTrue(Equal);
  }
}
