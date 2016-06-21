package a2;

import java.util.*;

public class Folder extends File {
  private String dirPath;
  private Vector children;
  private Vector<String> childrenNames;
  private boolean atRoot;

  public Folder(String name, String path) {
    super(name);
    this.dirPath = path;
    this.children = new Vector();
    this.childrenNames = new Vector();
    this.atRoot = false;
  }

  public String getPath() {
    return this.dirPath;
  }

  public void addChildren(Folder child) {
    this.children.addElement(child);
    this.childrenNames.addElement(child.getName());
  }

  public void addChildren(File child) {
    children.addElement(child);
    childrenNames.addElement(child.getName());
  }

  public Object getChildren(int index) {
    if (children.size() <= index) {
      return null;
    }
    return children.get(index);
  }

  public File getFile(String name) {
    Object result = null;
    for (Object fileOrFolder : children) {
      if (((File) fileOrFolder).getName().equals(name)) {
        result = fileOrFolder;
      }
    }
    return (File) result;
  }

  public Object getChildrenName(int index) {
    if (childrenNames.size() <= index) {
      return null;
    }
    return childrenNames.get(index);
  }

  public Vector getAllChildren() {
    if (children.size() == 0) {
      return null;
    }
    return children;
  }

  public String toString() {
    return super.getName() + dirPath;
  }

  public void isAtRoot(boolean root) {
    this.atRoot = root;
  }

  public boolean getisAtRoot() {
    return this.atRoot;
  }
  
}
