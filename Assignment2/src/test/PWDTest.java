package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class PWDTest {
  private JFileSystem jFileSystem;
  private PWD printWorkingDir;
  private ProQuery runCommand;

  @Before
  public void setUp() throws Exception {}

  {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
    this.printWorkingDir = new PWD(this.jFileSystem);
    this.runCommand = new ProQuery(this.jFileSystem);
  }

  @Test
  public void testPWDRootFolder() {
    /*
     * Test to determine if printing the current directory of the root folder
     * works
     * 
     * Expected output should be a string containing the path of the current
     * working directory ("/")
     */;
    assertEquals("/", printWorkingDir.execute());
  }

  @Test
  public void testPWDNewFolder() {
    /*
     * Test to determine if printing the current directory of a newly created
     * folder with depth 1 works
     * 
     * Expected output should be a string containing the path of the newly
     * created working directory ("/a")
     */
    runCommand.sortQuery("mkdir a");
    runCommand.sortQuery("cd a");
    printWorkingDir = new PWD(runCommand.getFileSystem());
    assertEquals("/a", printWorkingDir.execute());
  }

  @Test
  public void testPWDBranchingNewFolder() {
    /*
     * Test to determine if printing the current directory of a newly created
     * folder with depth 1 works alongside a branching folder
     * 
     * Expected output should be a string containing the path of the newly
     * created working directory ("/a")
     */
    runCommand.sortQuery("mkdir a");
    runCommand.sortQuery("mkdir b");
    runCommand.sortQuery("cd a");
    printWorkingDir = new PWD(runCommand.getFileSystem());
    assertEquals("/a", printWorkingDir.execute());
  }

  @Test
  public void testPWDDeepNewFolder() {
    /*
     * Test to determine if printing the current directory of a newly created
     * folder with depth 2 works
     * 
     * Expected output should be a string containing the path of the newly
     * created working directory ("/a/b")
     */
    runCommand.sortQuery("mkdir a");
    runCommand.sortQuery("cd a");
    runCommand.sortQuery("mkdir b");
    runCommand.sortQuery("cd b");
    printWorkingDir = new PWD(runCommand.getFileSystem());
    assertEquals("/a/b", printWorkingDir.execute());
  }

  @Test
  public void testPWDBranchingDeepNewFolder() {
    /*
     * Test to determine if printing the current directory of a newly created
     * folder with depth 1 works alongside a branching folder
     * 
     * Expected output should be a string containing the path of the newly
     * created working directory ("/a")
     */
    runCommand.sortQuery("mkdir a");
    runCommand.sortQuery("mkdir b");
    runCommand.sortQuery("mkdir c");
    runCommand.sortQuery("cd b");
    runCommand.sortQuery("mkdir d");
    runCommand.sortQuery("mkdir e");
    runCommand.sortQuery("mkdir f");
    runCommand.sortQuery("cd f");
    printWorkingDir = new PWD(runCommand.getFileSystem());
    assertEquals("/b/f", printWorkingDir.execute());
  }
}
