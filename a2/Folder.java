package a2;

import java.util.*;
public class Folder extends FileSystem{
  private String dirName;
  private String dirPath;
  private Vector children;
  
  public Folder(String name, String path){
    this.dirName = name;
    this.dirPath = path;
    this.children = new Vector();
  }
  public String getName(){
    return this.dirName;
  }
  public String getPath(){
    return this.dirPath;
  }
  public void addChildren(Folder child){
    children.addElement(child);
  }
  public void addChildren(File child){
    children.addElement(child);
  }
  public Object getChildren(int index){
    return children.get(index);
  }
  public String toString(){
    return dirName + dirPath;
  }
}
