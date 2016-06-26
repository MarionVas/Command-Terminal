package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.*;

public class MkdirTest 
{
  private JFileSystem jFileSystem;
  private String[] fileName;
  private String[] fileNames;
  private Mkdir mkdir1;
  private Mkdir mkdir2;
  private Mkdir mkdir3;

  @Before
  public void setUp() throws Exception 
  {
    jFileSystem = new JFileSystem();
    fileName = new String[1];
    fileNames = new String[3];
  }

  @Test
  public void testExecute1() 
  {
    fileName[0] = "a";
    mkdir1 = new Mkdir(jFileSystem, fileName);
    mkdir1.execute();
    assertTrue(jFileSystem.checkValidPath("/a"));
  }

}
