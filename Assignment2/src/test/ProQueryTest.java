package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class ProQueryTest 
{
  private JFileSystem jFileSystem;
  private ProQuery process;
  private String query;

  @Before
  public void setUp() throws Exception 
  {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);
    
    process = new ProQuery(jFileSystem);
  }

  @Test
  public void testSortQuery() 
  {
    query = "mkdir bananas";
    process.sortQuery(query);
    assertTrue(process.getFileSystem().checkValidPath("/bananas"));
  }
  
  @Test
  public void testSortQuery1() 
  {
    query = "mkdir bananas waka tests";
    process.sortQuery(query);
    assertTrue(process.getFileSystem().checkValidPath("/tests"));
  }
  
  @Test
  public void testSortQuery2() 
  {
    query = "mkdir bananas waka tests";
    process.sortQuery(query);
    query = "mkdir /bananas/potassium";
    process.sortQuery(query);
    assertTrue(process.getFileSystem().checkValidPath("/bananas/potassium"));
  }
  
  @Test
  public void testSortQuery3() 
  {
    query = "mkdir a b c";
    process.sortQuery(query);
    query = "mkdir /a/a1";
    process.sortQuery(query);
    query = "cd /a/a1";
    process.sortQuery(query);
    assertEquals("/a/a1", process.getFileSystem().getCurrPath());
  }
  
  @Test
  public void testSortQuery4() 
  {
    query = "mkdir *******";
    process.sortQuery(query);
    assertFalse(process.getFileSystem().checkValidPath("*******"));
  }
  
  @Test
  public void testSortQuery5() 
  {
    query = "mkdont banana";
    process.sortQuery(query);
    assertFalse(process.getFileSystem().checkValidPath("banana"));
  }
  
  @Test
  public void testSortQuery6() 
  {
    query = "mkdir bananas waka tests";
    process.sortQuery(query);
    query = "mkdir /bananas/potassium";
    process.sortQuery(query);
    query = "cf /bananas/potassium";
    assertEquals("/", process.getFileSystem().getCurrPath());
  }
  
  @Test
  public void testSortQuery7() 
  {
    query = "mkdir                    bananas";
    process.sortQuery(query);
    query = "mkdir /bananas/potassium";
    process.sortQuery(query);
    assertTrue(process.getFileSystem().checkValidPath("/bananas/potassium"));
  }
  
  @Test
  public void testSortQuery8() 
  {
    query = "mkdir                    bananas           test";
    process.sortQuery(query);
    assertTrue(process.getFileSystem().checkValidPath("/test"));
  }
  
  @Test
  public void testSortQuery9() 
  {
    query = "mkdir a              b c";
    process.sortQuery(query);
    query = "mkdir /a/a1     ";
    process.sortQuery(query);
    query = "cd          /a/a1";
    process.sortQuery(query);
    assertEquals("/a/a1", process.getFileSystem().getCurrPath());
  }


}
