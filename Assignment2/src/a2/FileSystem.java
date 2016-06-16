package a2;
import java.util.*;
public class FileSystem {
  //Output collaboration
  //DirStack collaboration
  //Vector Coordinates; //Unessercsay
  private Vector Manager;
  private Vector<String> fullPaths;
  private String currDir;
  private Folder empty; //To be removed
  //private int currDepth;
  
  public FileSystem(){
    //Coordinates = new Vector();
    this.Manager = new Vector();
    this.fullPaths = new Vector();
    this.fullPaths.addElement("/");
    this.fullPaths.addElement("");
    //empty = new Folder("empty", "/");
    this.Manager.addElement(empty); //To be removed
    currDir = "/";
    //this.currDepth = 0;
  }
  public String getCurrPath(){
   return this.currDir;
  }
  public void setFullPath(String newDir){
    this.currDir = newDir;
  }
  public Object getObject(int index){
    //System.out.println("getting" + Manager.get(0));
    return Manager.get(index);
  }

  public Object getObject(String name) {
    Object result = null;
    if (name.startsWith("/")) {
      /*
       * String parentName = name.substring(name.lastIndexOf("/") + 1,
       * name.length());
       */ //To be removed
      for (int i = 0; i < Manager.size(); i++) {
        if (Manager.get(i).getClass().equals(Folder.class)) {
          if (((Folder) Manager.get(i)).getPath() == name) {
            result = Manager.get(i);
          }
        }
        /*
         * else{ if (((File) Manager.get(i)).getPath() == name) { result =
         * Manager.get(i); } }
         */ // To be taken out of comment latter

      }
    } else {
      for (int i = 0; i < Manager.size(); i++) {
        if (Manager.get(i).getClass().equals(Folder.class)) {
          if (((Folder) Manager.get(i)).getName() == name) {
            result = Manager.get(i);
          }
        }
        /*
         * else{ if (((File) Manager.get(i)).getName() == name){ result =
         * Manager.get(i); } }
         */ // To be taken out of comment later

      }
    }
    return result;
  }
  public void add(Folder newFolder){
    //System.out.println(newFolder);
    Manager.add(newFolder);
    fullPaths.add(newFolder.getPath());
    //this.currDepth++;
    //System.out.println("added");
  }
  public void add(File newFile){
    Manager.addElement(newFile);
    //fullPaths.add(newFile.getPath()); To be taken out of comment later
  }
  public Vector<String> getFullPaths(){
    return this.fullPaths;
  }
  public boolean checkValidPath(String path){
    return this.fullPaths.contains(path);
  }
  public boolean relativePathCehcker(String path){
    boolean valid = false;
    for (int i = 0; i < this.fullPaths.size(); i++){
      if (this.fullPaths.get(i).contains(path)){
        valid = true;
      }
    }
    return  valid;
  }
  /*public int getDepth(){
    return this.currDepth;
  }
  public void decreaseDepth(){
    this.currDepth--;
  }
  public void increaseDepth(){
    this.currDepth++;
  }*/ //to be removed
}
