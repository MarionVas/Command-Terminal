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
