package test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import a2.CD;
import a2.DirStack;
import a2.File;
import a2.Folder;
import a2.JFileSystem;
import a2.Mkdir;

public class FolderTest {
  private JFileSystem jFileSystem;
  private Vector<Object> children;
  private Vector<String> childrenNames;
  private Folder newFolder;
  private Folder rootFolder;
  private File newFile;

  @Before
  public void setUp() throws Exception {
    this.jFileSystem = new JFileSystem();
    this.rootFolder = new Folder("/", "/");
    this.children = new Vector<Object>();
    this.childrenNames = new Vector<String>();
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
    newFolder = new Folder("Test", "/Test");
    newFile = new File("Test String");
    rootFolder.addChildren(newFolder);
    rootFolder.addChildren(newFile);


  }

  @Test
  public void testgetPath() {
    /*
     * Seeing if the correct path can be retrieved
     * 
     * Expected output: The absolute path of the Folder
     */
    assertEquals("/Test", this.newFolder.getPath());
  }

  @Test
  public void testgetName() {
    /*
     * Seeing if the name of the folder can be properly retrieved
     * 
     * Expected output: The name of the Folder
     */
    assertEquals("Test", this.newFolder.getName());
  }

  @Test
  public void testgetChildren() {
    /*
     * Testing if the proper thing is returned if there are no children
     * 
     * Expected output: a null, since there are no children in the folder
     */
    assertEquals(null, this.newFolder.getChildren(1));
  }

  @Test
  public void testgetChildren2() {
    /*
     * Testing if a child of a folder can be retrieved
     * 
     * Expected output: A Folder object
     */
    assertEquals(this.newFolder, this.rootFolder.getChildren(0));
  }

  @Test
  public void testgetChildren3() {
    /*
     * Testing if a file can be retrieved
     * 
     * Expected output: A File object
     */
    assertEquals(this.newFile, this.rootFolder.getChildren(1));
  }

  @Test
  public void testgetFile() {
    /*
     * Determining if the getFile function works properly
     * 
     * Expected output: the name of the file
     */
    assertEquals(this.newFile, this.rootFolder.getFile("Test String"));
  }

  @Test
  public void testgetChildrenName() {
    /*
     * Testing if name of a child of a folder can be retrieved if there are not
     * children
     * 
     * Expected output:a null
     */
    assertEquals(null, this.newFolder.getChildrenName(1));
  }

  @Test
  public void testgetChildrenName2() {
    /*
     * Testing if a child (FOLDER) of a folder can be retrieved
     * 
     * Expected output: A name of one of the children of rootFolder
     */
    assertEquals(this.newFolder.getName(), this.rootFolder.getChildrenName(0));
  }

  @Test
  public void testgetChildrenName3() {
    /*
     * Testing if a child (FILE) of a folder can be retrieved
     * 
     * Expected output: A name of one of the children of rootFolder
     */
    assertEquals(this.newFile.getName(), this.rootFolder.getChildrenName(1));
  }

  @Test
  public void testGetAllChildren() {
    /*
     * Seeing if all the children can be retrieved at once
     * 
     * Expected output: A vector containing all the children
     */
    this.children.addElement(newFolder);
    this.children.addElement(newFile);
    assertEquals(this.children, this.rootFolder.getAllChildren());
  }

  @Test
  public void testGetAllChildren2() {
    /*
     * Seeing if all the children can be retrieved at once if there are NO
     * children
     * 
     * Expected output: A null since there are no children
     */
    assertEquals(null, this.newFolder.getAllChildren());
  }

  @Test
  public void testGetAllChildrenNames() {
    /*
     * Seeing if all the children names can be retrieved at once
     * 
     * Expected output: A vector containing all the children names
     */
    this.childrenNames.addElement(newFolder.getName());
    this.childrenNames.addElement(newFile.getName());
    assertEquals(this.childrenNames, this.rootFolder.getAllChildrenNames());
  }

  @Test
  public void testGetAllChildrenNames2() {
    /*
     * Seeing if all the children names can be retrieved at once if there are NO
     * children
     * 
     * Expected output: A null since there are no children
     */
    assertEquals(null, this.newFolder.getAllChildrenNames());
  }
}
