package a2;

import java.util.*;

public class FileSystem {
  // Output collaboration
  // DirStack collaboration
  // Vector Coordinates; //Unessercsay
  private Vector<Folder> Manager;
  private Vector<String> fullPaths;
  private String currDir;
  private Folder currFolder; // DON'T REMOVE THIS (David)
  // private int currDepth;

  public FileSystem() {
    // Coordinates = new Vector();
    this.Manager = new Vector();
    this.fullPaths = new Vector();
    this.fullPaths.addElement("/");
    this.fullPaths.addElement("");
    // empty = new Folder("empty", "/");
    // this.Manager.addElement(empty); //To be removed
    this.currDir = "/"; // this. was missing did you miss it on purpose? (David)
    // this.currDepth = 0;
    this.currFolder = new Folder("/", "/"); // maybe change the name (David)
  }

  public String getCurrPath() {
    return this.currDir;
  }

  public void setFullPath(String newDir) {
    this.currDir = newDir;
  }

  public Object getObject(int index) {
    // System.out.println("getting" + Manager.get(0));
    return Manager.get(index);
  }

  public Object getObject(String name) {
    // Only accepts an absolute path
    Object result = null;
    if (name.startsWith("/")) {

      for (int i = 0; i < Manager.size(); i++) {
        if (result == null) {
          result = getObjRecurs(name, "/" + ((Folder) Manager.get(i)).getName(),
              Manager.get(i));
        }
      }

    } else {
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

  public Object getObjRecurs(String name, String currName, Folder dirrOrFile) {
    /*
     * System.out.println(((currName+dirrOrFile.getName()).equals(name)));
     * System.out.println((currName+dirrOrFile.getName() == (name)));
     * System.out.println(currName + "     " + dirrOrFile.getName() + "      " +
     * name);
     */
    if (dirrOrFile == null) {
      Object result = null;
    } else if (currName.equals(name)) {
      return dirrOrFile;
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
      }

    }
    return result;
  }

  public void add(Folder newFolder) {
    // System.out.println(newFolder);
    Manager.add(newFolder);
    fullPaths.add(newFolder.getPath());
    // this.currDepth++;
    // System.out.println("added");
  }

  /*
   * public void add(File newFile){ Manager.addElement(newFile);
   * //fullPaths.add(newFile.getPath()); To be taken out of comment later }
   */
  public Vector<String> getFullPaths() {
    return this.fullPaths;
  }

  public void addFullPath(String path) {
    this.fullPaths.addElement(path);
  }

  public boolean checkValidPath(String path) {
    return this.fullPaths.contains(path);
  }

  public boolean relativePathChecker(String path) { // you spelled the name
                                                    // wrong (David)
    boolean valid = false;
    for (int i = 0; i < this.fullPaths.size(); i++) {
      if (this.fullPaths.get(i).contains(path)) {
        valid = true;
      }
    }
    return valid;
  }

  public void setCurrFolder(Folder folder) {
    this.currFolder = folder;
  }

  public Folder getCurrFolder() {
    return this.currFolder;
  }
  /*
   * public int getDepth(){ return this.currDepth; } public void
   * decreaseDepth(){ this.currDepth--; } public void increaseDepth(){
   * this.currDepth++; }
   */ // to be removed

}


