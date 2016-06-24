package a2;

import java.util.*;

public class Folder extends File {
  // The absolute path of the folder
  private String dirPath;
  // A vector containing all the children Folder/Files
  private Vector children;
  // A vector containing all the names of the sub folders/files
  private Vector<String> childrenNames;

  /**
   * The constructor
   * 
   * @param name - The name of the Folder (directory)
   * @param path - The absolute path of the Folder
   */
  public Folder(String name, String path) {
    // Calling the parent class
    super(name);
    this.dirPath = path;
    this.children = new Vector();
    this.childrenNames = new Vector();
  }

  /**
   * A getting used to retrieve the absolute path of the folder
   * 
   * @return
   */
  public String getPath() {
    return this.dirPath;
  }

  /**
   * Adds children (A Folder) to this Folder
   * 
   * @param child - A sub-Folder to this one
   */
  public void addChildren(Folder child) {
    // Adding the object
    this.children.addElement(child);
    // Adding the Folder name
    this.childrenNames.addElement(child.getName());
  }

  /**
   * Adds children (A File) to this Folder
   * 
   * @param child - A sub-File to this one
   */
  public void addChildren(File child) {
    // Adding the object
    children.addElement(child);
    // Adding the File name
    childrenNames.addElement(child.getName());
  }

  /**
   * Gets the child from the vector at the specified index; Returns null if no
   * children are present
   * 
   * @param index - an index value
   * @return - A Folder or File Object
   */
  public Object getChildren(int index) {
    if (children.size() <= index) {
      return null;
    }
    return children.get(index);
  }

  /**
   * Gets the a File that is present in the folder
   * 
   * @param name - The name of a file
   * @return result - A file
   */
  public File getFile(String name) {
    Object result = null;
    // Checks for all the children in this Folder
    for (Object fileOrFolder : children) {
      if (((File) fileOrFolder).getName().equals(name)) {
        result = fileOrFolder;
      }
    }
    return (File) result;
  }

  /**
   * Gets the child name at the specified index; returns null if there are no
   * children
   * 
   * @param index - an index value
   * @return - A string representing the name of a object
   */
  public Object getChildrenName(int index) {
    if (childrenNames.size() <= index) {
      return null;
    }
    return childrenNames.get(index);
  }

  /**
   * A getter used to retrieve the entire vector containing the child objects;
   * Returns null if no children are present
   * 
   * @return children - A vector containing child object
   */
  public Vector getAllChildren() {
    if (children.size() == 0) {
      return null;
    }
    return children;
  }

  /**
   * A getter used to retrieve the entire vector containing the child objects
   * names; Returns null if there are no children present
   * 
   * @return children - A vector containing child object names
   */
  public Vector getAllChildrenNames() {
    if (childrenNames.size() == 0) {
      return null;
    }
    return childrenNames;
  }

  /**
   * Return a string representation of the name and the path of the Folder
   */
  public String toString() {
    return super.getName() + dirPath;
  }
}
