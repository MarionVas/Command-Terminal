package a2;

import java.awt.Component;
import java.awt.List;
import java.util.Vector;

public class LS implements CommandInterface{
  private FileSystem Manager;
  public LS(FileSystem fileManager){
    this.Manager = fileManager;
  }
  public String execute(String arg){
    String contents = "";
    if (arg == ""){
      if (Manager.getCurrPath() != "/"){
        Folder currFolder = (Folder) Manager.getObject(Manager.getCurrPath());
        Vector childNames = currFolder.getAllChildren();
        java.util.Collections.sort(childNames);
        int i = 0;
        while (currFolder.getChildren(i) != null){
          contents = contents + "\n" + childNames.get(i);
        }
      }
      else{
        int index = 0;
        Vector childNames = new Vector();
        while (Manager.getObject(index) != null){
          childNames.add(((Folder) Manager.getObject(index)).getName());
        }
        java.util.Collections.sort(childNames);
        int i = 0;
        while (childNames.get(i) != null){
          contents = contents + "\n" + childNames.get(i);
        }
      }
    }
    else if (arg.startsWith("/")){
      if (Manager.checkValidPath(arg)){
        if (Manager.getCurrPath() != "/"){
          Folder currFolder = (Folder) Manager.getObject(arg);
          Vector childNames = currFolder.getAllChildren();
          java.util.Collections.sort(childNames);
          int i = 0;
          while (currFolder.getChildren(i) != null){
            contents = contents + "\n" + childNames.get(i);
          }
          contents = arg + ": " + contents + "\n";
        }
        else{
          int index = 0;
          Vector childNames = new Vector();
          while (Manager.getObject(index) != null){
            childNames.add(((Folder) Manager.getObject(index)).getName());
          }
          java.util.Collections.sort(childNames);
          int i = 0;
          while (childNames.get(i) != null){
            contents = contents + "\n" + childNames.get(i);
          }
          contents = arg + ": " + contents + "\n";
        }
      }
      else{
        //Print error
      }
    }
    else if (arg.startsWith("..")){
      int lastIndex = 0;
      int count = 0;

      while(lastIndex != -1){

          lastIndex = arg.indexOf("..",lastIndex);

          if(lastIndex != -1){
              count ++;
              lastIndex += 2;
          }
      }
      String path = "";
      for (int i = 1; i < Manager.getCurrPath().split("/").length - count; i++){
        path =  path + "/" +  Manager.getCurrPath().split("/")[i];
      }
      arg = path + arg;
      
      if (Manager.checkValidPath(arg)){
        if (Manager.getCurrPath() != "/"){
          Folder currFolder = (Folder) Manager.getObject(arg);
          Vector childNames = currFolder.getAllChildren();
          java.util.Collections.sort(childNames);
          int i = 0;
          while (currFolder.getChildren(i) != null){
            contents = contents + "\n" + childNames.get(i);
          }
          contents = arg + ": " + contents + "\n";
        }
        else{
          int index = 0;
          Vector childNames = new Vector();
          while (Manager.getObject(index) != null){
            childNames.add(((Folder) Manager.getObject(index)).getName());
          }
          java.util.Collections.sort(childNames);
          int i = 0;
          while (childNames.get(i) != null){
            contents = contents + "\n" + childNames.get(i);
          }
          contents = arg + ": " + contents + "\n";
        }
      }
      else{
        //Print error
      }
    }
    else if (arg.contains("/")){
      arg = Manager.getCurrPath() + "/" + arg;
      if (Manager.checkValidPath(arg)){
        Folder currFolder = (Folder) Manager.getObject(arg);
        Vector childNames = currFolder.getAllChildren();
        java.util.Collections.sort(childNames);
        int i = 0;
        while (currFolder.getChildren(i) != null){
          contents = contents + "\n" + childNames.get(i);
        }
        contents = arg + ": " + contents + "\n";
      }
      else{
        //Print error
      }
    }
    else {
      //File case
    }
    return contents;
  }
  
}
