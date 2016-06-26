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
  public void setUp() throws Exception 
  {
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
    assertEquals("/Test", this.newFolder.getPath());
  }
  @Test
  public void testgetName() {
    assertEquals("Test", this.newFolder.getName());
  }
  @Test
  public void testgetChildren() {
    assertEquals(null, this.newFolder.getChildren(1));
  }
  @Test
  public void testgetChildren2() {
    assertEquals(this.newFolder, this.rootFolder.getChildren(0));
  }
  @Test
  public void testgetChildren3() {
    assertEquals(this.newFile, this.rootFolder.getChildren(1));
  }
  @Test
  public void testgetFile() {
    assertEquals(this.newFile, this.rootFolder.getFile("Test String"));
  }
  @Test
  public void testgetChildrenName() {
    assertEquals(null, this.newFolder.getChildrenName(1));
  }
  @Test
  public void testgetChildrenName2() {
    assertEquals(this.newFolder.getName(), this.rootFolder.getChildrenName(0));
  }
  @Test
  public void testgetChildrenName3() {
    assertEquals(this.newFile.getName(), this.rootFolder.getChildrenName(1));
  }
  @Test
  public void testGetAllChildren(){
    this.children.addElement(newFolder);
    this.children.addElement(newFile);
    assertEquals(this.children, this.rootFolder.getAllChildren());
  }
  @Test
  public void testGetAllChildren2(){
    assertEquals(null, this.newFolder.getAllChildren());
  }
  @Test
  public void testGetAllChildrenNames(){
    this.childrenNames.addElement(newFolder.getName());
    this.childrenNames.addElement(newFile.getName());
    assertEquals(this.childrenNames, this.rootFolder.getAllChildrenNames());
  }
  @Test
  public void testGetAllChildrenNames2(){
    assertEquals(null, this.newFolder.getAllChildrenNames());
  }
}
