package a2;
import java.util.*;
public class FileSystem {
  //Output collaboration
  //DirStack collaboration
  Vector Coordinates;
  Vector Manager;
  private String currDir;
  
  public FileSystem(){
    Coordinates = new Vector();
    Manager = new Vector();
    currDir = "/";
  }
  public String getCurrPath(){
   return this.currDir;
  }
  public void setFullPath(String newDir){
    this.currDir = newDir;
  }
}