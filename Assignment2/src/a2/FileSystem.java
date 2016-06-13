package a2;
import java.util.*;
public class FileSystem {
  //Output collaboration
  //DirStack collaboration
  //Vector Coordinates; //Unessercsay
  private Vector Manager;
  private Vector<String> fullPaths;
  private String currDir;
  
  public FileSystem(){
    //Coordinates = new Vector();
    this.Manager = new Vector();
    this.fullPaths = new Vector();
    currDir = "/";
  }
  public String getCurrPath(){
   return this.currDir;
  }
  public void setFullPath(String newDir){
    this.currDir = newDir;
  }
  public Object getObject(int index){
    return Manager.get(index);
  }
  public Object getObject(String name) {
    Object result = null;
    if (name.startsWith("/")) {
      String parentName =
          name.substring(name.lastIndexOf("/") + 1, name.length());
      for (int i = 0; i < Manager.size(); i++) {
        if (Manager.get(i) == parentName) {
          result = Manager.get(i);
        }
      }
    }
    else{
      for (int i = 0; i < Manager.size(); i++){
        if (Manager.get(i) == name){
          result = Manager.get(i);
        }
      }
    }
    return result;
  }
  public void add(Folder newFolder){
    Manager.addElement(newFolder);
    fullPaths.add(newFolder.getPath());
  }
  public void add(File newFile){
    Manager.addElement(newFile);
    //fullPaths.add(newFile.getPath());
  }
  public Vector<String> getFullPaths(){
    return this.fullPaths;
  }
}
