package a2;

import java.util.*;
public class Folder extends FileSystem{
  private String dirName;
  private String dirPath;
  private Vector children;
  private Vector<String> childrenNames;
  
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
    this.children.addElement(child);
    this.childrenNames.addElement(child.getName());
  }
  public void addChildren(File child){
    children.addElement(child);
  }
  public Object getChildren(int index){
    if (children.size() <= index){
      return null;
    }
    return children.get(index);
  }
  public Object getChildrenName(int index){
    if (childrenNames.size() <= index){
      return null;
    }
    return childrenNames.get(index);
  }
  public String toString(){
    return dirName + dirPath;
  }
}
