package a2;

import java.util.*;

public class FileSystem {
  // Output collaboration
  // DirStack collaboration
  // A vector which holds all Directories at root
  private Vector<Folder> Manager;
  // A vector that holds all the full paths of each entity in JShell
  private Vector<String> fullPaths;
  // A string representing the current working directory
  private String currDir;
  // The current working folder
  private Folder currFolder;

  /**
   * The constructor
   */
  public FileSystem() {
    this.Manager = new Vector();
    this.fullPaths = new Vector();
    // Adding the two most basic paths
    this.fullPaths.addElement("/");
    this.fullPaths.addElement("");
    // The root directory
    this.currDir = "/";
  }
  /**
   * This function return the path of the current working directory.To be used 
   * with various command classes.
   * 
   * @return currDirr - The absolute path of the current working directory
   */
  public String getCurrPath() {
    return this.currDir;
  }

  /**
   * This method accepts an absolute path and sets that as the current working
   * directory. To only be used by CD
   * 
   * @param newDir - Represents the new absolute path for the new working
   *        directory
   */
  public void setFullPath(String newDir) {
    this.currDir = newDir;
  }

  /**
   * This function gets one of the Directories present at root. It takes in an
   * index position as a parameter.To be used with various command classes.
   * 
   * @param index - An integer representing an index position
   * @return returns the directory at the specified index of Manager
   */
  public Object getObject(int index) {
    return Manager.get(index);
  }

  /**
   * This function accepts an absolute path or a name of an object in the local
   * directory and returns the corresponding directory or File according the the
   * path. Traverses through the all the Trees that exist at root. To be used 
   * with various command classes.
   * 
   * @param name - A string representing an absolute path or a name of a local
   *        directory/file
   * @return - An object at the location of the specified absolute path
   */
  public Object getObject(String name) {
    // The object that is to be returned
    Object result = null;
    // If an absolute path is provided
    if (name.startsWith("/")) {
      // A loop which checks each tree present at root
      for (int i = 0; i < Manager.size(); i++) {
        // Only runs if the object is not found
        if (result == null) {
          // Sends the given absolute path, the initial path of the current
          // tree, and the head of the tree
          result = getObjRecurs(name, "/" + ((Folder) Manager.get(i)).getName(),
              Manager.get(i));
        }
      }

    } else { // If a local directory name is given
      // turning the local directory name into an absolute path
      name = this.currDir + "/" + name;
      for (int i = 0; i < Manager.size(); i++) {
        if (result == null) {
          result = getObjRecurs(name, "/" + ((Folder) Manager.get(i)).getName(),
              Manager.get(i));
        }
      }
    }
    return result;
  }

  /**
   * This is the recursive helper function to getObject() which searches through
   * the tree. With each recursive step it checks a new child, and adds to
   * the previous path until either, the desired object is found or the end of
   * the subtree is reached.
   * 
   * @param name - The absolute path of the desired directory or file
   * @param currName - The current path built by the previous recursive steps
   * @param dirrOrFile
   * @return result - Can either be the desired object or a null type
   */
  public Object getObjRecurs(String name, String currName, Folder dirrOrFile) {

    if (currName.equals(name)) {
      return dirrOrFile;
    } else if (dirrOrFile == null) {
      Object result = null;
    }
    Vector allChildren = dirrOrFile.getAllChildren();
    Object result = null;
    for (int i = 0; result == null && allChildren != null
        && i < allChildren.size(); i++) {
      if (allChildren.get(i).getClass().equals(Folder.class)) {
        result = getObjRecurs(name,
            currName + "/" + ((Folder) allChildren.get(i)).getName(),
            (Folder) allChildren.get(i));
      } else {
        // get File's name and check
        result = getObjRecurs(name,
            currName + "/" + ((File) allChildren.get(i)).getName(), null);
      }

    }
    return result;
  }

  public void add(Folder newFolder) {
    Manager.add(newFolder);
    fullPaths.add(newFolder.getPath());
  }

  public void addFilePath(String path) {
    // Manager.addElement(newFile);
    fullPaths.add(path);
  }

  public Vector<String> getFullPaths() {
    return this.fullPaths;
  }

  public void addFullPath(String path) {
    this.fullPaths.addElement(path);
  }

  public boolean checkValidPath(String path) {
    return this.fullPaths.contains(path);
  }
  
  public void setCurrFolder(Folder folder) {
    this.currFolder = folder;
  }

  public Folder getCurrFolder() {
    return this.currFolder;
  }
}


