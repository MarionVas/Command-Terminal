package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class CDTest {

  private JFileSystem jFileSystem;
  private CD cd1;
  private CD cd2;
  private CD cd3;
  private Mkdir mkdir1;
  private Mkdir mkdir2;
  private Mkdir mkdir3;
  private Mkdir mkdir4;
  private String[] location;

  @Before
  public void setUp() throws Exception 
  {
    this.jFileSystem = new JFileSystem();
    Folder rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    String[] path = {"a", "b", "c"};
    String[] path2 = {"/a/a1"};
    String[] path3 = {"/a/a1/a2"};
    String[] path4 = {"/a/a1/alternate"};
    mkdir1 = new Mkdir(jFileSystem, path);
    mkdir1.execute();
    mkdir2 = new Mkdir(jFileSystem, path2);
    mkdir2.execute();
    mkdir3 = new Mkdir(jFileSystem, path3);
    mkdir3.execute();
    mkdir4 = new Mkdir(jFileSystem, path4);
    mkdir4.execute();
    
    location = new String[1];
    
  }

  @Test
  public void testExecute() 
  {
    location[0] = "a";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a", jFileSystem.getCurrPath());
  }
  
  @Test
  public void testExecute1() 
  {
    location[0] = "a/a1";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1", jFileSystem.getCurrPath());
  }
  
  @Test
  public void testExecute2() 
  {
    location[0] = "a/a1/a2";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }
  
  @Test
  public void testExecute3() 
  {
    location[0] = "a";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "a1";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "a2";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }
  
  @Test
  public void testExecute4() 
  {
    location[0] = "a";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "a1";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "a2";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "..";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1", jFileSystem.getCurrPath());
  }
  
  @Test
  public void testExecute5() 
  {
    location[0] = "a/a1/a2/";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }
  
  @Test
  public void testExecute6() 
  {
    location[0] = "a/a1/a2/";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = ".";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }
  
  @Test
  public void testExecute7() 
  {
    location[0] = "random";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/", jFileSystem.getCurrPath());
  }
  
  @Test
  public void testExecute8() 
  {
    location[0] = "a";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "a1";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "alternate";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "..";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    location[0] = "random";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1", jFileSystem.getCurrPath());
  }
  
  @Test
  public void testExecute9() 
  {
    location[0] = "/a/a1/a2";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }
  
  @Test
  public void testExecute10() 
  {
    location[0] = "/a/a1/../a1/./a2/";
    cd1 = new CD(jFileSystem, location);
    cd1.execute();
    assertEquals("/a/a1/a2", jFileSystem.getCurrPath());
  }

}
