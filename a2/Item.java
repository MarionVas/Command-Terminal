package a2;

import java.util.Vector;

public class Item {
    private Vector<Item> children;
    private Vector<String> childrenNames;
    private String name;
    private String path;
    
    public Item(String name, String path,Vector children, Vector<String> childrenName){
      this.name = name;
      this.path = path;
      this.children = children;
      this.childrenNames = childrenName;
    }

    public String getPath() {
      return path;
    }

    public void setPath(String path) {
      this.path = path;
    }

    public Vector<Item> getChildren() {
      return children;
    }

    public Vector<String> getChildrenNames() {
      return childrenNames;
    }

    public String getName() {
      return name;
    }
    
    public void setName(String name){
      this.name = name;
    }
}
