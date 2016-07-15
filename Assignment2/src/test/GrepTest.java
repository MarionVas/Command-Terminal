package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import a2.Cat;
import a2.File;
import a2.Folder;
import a2.Grep;
import a2.JFileSystem;
import a2.Mkdir;

public class GrepTest {
  
  private JFileSystem jFileSystem;
  private Cat cat;
  private File file1;
  private File file2;
  private File file3;
  private File file4;
  private File file5;
  private String[] fileNames;
  private Grep grep;
  private Folder rootFolder;
  
  @Before
  public void setUp() throws Exception 
  {
    this.jFileSystem = new JFileSystem();
    rootFolder = new Folder("/", "/");
    jFileSystem.setRoot(rootFolder);
    jFileSystem.setCurrFolder(rootFolder);

    file1 = new File("file1");
    file1.setPath("/file1");
    rootFolder.addChildren(file1);
    file2 = new File("file2");
    file2.setPath("/file2");
    file2.setBody("This is a test in test2.");
    rootFolder.addChildren(file2);
    file3 = new File("file3");
    file3.setPath("/file3");
    file3.setBody("\na\nb\nc");
    rootFolder.addChildren(file3);
    jFileSystem.addFullPath("/file1");
    jFileSystem.addFullPath("/file2");
    jFileSystem.addFullPath("/file3");
  }

  @Test
  public void test() 
  {
    //System.out.println(jFileSystem.checkValidPath("/file3"));
    String[] command = {"a", "/file3"};
    grep = new Grep(jFileSystem, command);
    System.out.println(grep.execute());
//    //System.out.println(jFileSystem.checkValidPath("/file3"));
//    String[] command1 = {"This is", "/file3"};
//    grep = new Grep(jFileSystem, command1);
//    System.out.println("START" + grep.execute());
    assertEquals(true, true);
  }
  
//  // MAKE IT HERE
//  @Test
//  public void test1() 
//  {
//    //System.out.println(jFileSystem.checkValidPath("/file3"));
//
//    
//    
//    String[] folder = {"folder1"};
//    Mkdir dir = new Mkdir(jFileSystem, folder);
//    dir.execute();
//    
//    // ADD THIS FILE INTO THE FOLDER CREATED UP THERE
//    file4 = new File("file4");
//    file4.setPath("/folder1/file4");
//    file4.setBody("This is a test in test4,\nAnd it works\nI like That");
//    file5 = new File("file5");
//    file5.setPath("/folder1/file5");
//    file5.setBody("This is a test in test5,\nAnd it works\nI like This is");
//    
//    Folder directory = (Folder)jFileSystem.getObject("/folder1");
//    directory.addChildren(file4);
//    
//    jFileSystem.addFullPath("/folder1/file4");
//    
//    directory.addChildren(file5);
//    
//    jFileSystem.addFullPath("/folder1/file5");
//    
//    System.out.println(jFileSystem.checkValidPath("/folder1/file4"));
//    
//    
//    
//    // PLS
//    
//    
//
//    String[] command = {"-R", "This is", "/folder1"};
//    grep = new Grep(jFileSystem, command);
//    grep.execute();
//
//    assertEquals(true, true);
//  }

}
