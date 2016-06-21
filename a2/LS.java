package a2;

import java.awt.Component;
import java.awt.List;
import java.util.Vector;

public class LS implements CommandInterface{
  private FileSystem Manager;
  private String[] args;
  private String arg = "";
  public LS(JFileSystem fileManager, String[] name){
    this.Manager = fileManager;
    this.args = name;
  }
  public LS(JFileSystem fileManager){
    this.Manager = fileManager;
    this.args = new String[1];
    this.args[0] = "";
  }
  public void execute(){
    for (int indexarg = 0 ; indexarg < this.args.length; indexarg++){
      this.arg = args[indexarg];
    
      String contents = "";
      if (arg == ""){
        if (Manager.getCurrPath() != "/"){
          Folder currFolder = (Folder) Manager.getObject(Manager.getCurrPath());
          Vector childNames = currFolder.getAllChildrenNames();
          if (childNames != null){
            java.util.Collections.sort(childNames);
          }
          int i = 0;
          while (currFolder.getChildren(i) != null){
            contents = contents + "\n" + childNames.get(i);
            i++;
          }
        }
        else{
          int index = 0;
          Vector childNames = new Vector();
          while (Manager.getObject(index) != null){
            childNames.add(((Folder) Manager.getObject(index)).getName());
            index++;
          }
          if (childNames != null){
            java.util.Collections.sort(childNames);
          }
          int i = 0;
          while (childNames.get(i) != null){
            contents = contents + "      " + childNames.get(i);
            i++;
          }
          contents = contents + "\n";
        }
      }
      else if (arg.startsWith("/")){
        if (Manager.checkValidPath(arg)){
          if (Manager.getCurrPath() != "/"){
            if (Manager.getObject(arg).getClass().equals(Folder.class)){
              
              Folder currFolder = (Folder) Manager.getObject(arg);
              Vector childNames = currFolder.getAllChildrenNames();
              if (childNames != null){
                java.util.Collections.sort(childNames);
              }
              int i = 0;
              while (currFolder.getChildren(i) != null){
                contents = contents + "     " + childNames.get(i);
                i++;
              }
              contents = arg + ": " + contents + "\n";
            }
            else {
              contents = arg + "\n";
            }
          }
          else{
            int index = 0;
            Vector childNames = new Vector();
            while (Manager.getObject(index) != null){
              childNames.add(((Folder) Manager.getObject(index)).getName());
              index++;
            }
            if (childNames != null){
              java.util.Collections.sort(childNames);
            }
            int i = 0;
            while (childNames.get(i) != null){
              contents = contents + "    " + childNames.get(i);
              i++;
            }
            contents = arg + ": " + contents + "\n";
          }
        }
        else{
          Output.printPathError();
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
            if (Manager.getObject(arg).getClass().equals(Folder.class)){
              Folder currFolder = (Folder) Manager.getObject(arg);
              Vector childNames = currFolder.getAllChildrenNames();
              if (childNames != null){
                java.util.Collections.sort(childNames);
              }
              int i = 0;
              while (currFolder.getChildren(i) != null){
                contents = contents + "     " + childNames.get(i);
                i++;
              }
              contents = arg + ": " + contents + "\n";
            }
            else {
              contents = arg + "\n";
            }
          }
          else{
            int index = 0;
            Vector childNames = new Vector();
            while (Manager.getObject(index) != null){
              childNames.add(((Folder) Manager.getObject(index)).getName());
              index++;
            }
            if (childNames != null){
              java.util.Collections.sort(childNames);
            }
            int i = 0;
            while (childNames.get(i) != null){
              contents = contents + "      " + childNames.get(i);
              i++;
            }
            contents = arg + ": " + contents + "\n";
          }
        }
        else{
          Output.printPathError();
        }
      }
      else if (arg.contains("/")){
        arg = Manager.getCurrPath() + "/" + arg;
        if (Manager.checkValidPath(arg)){
            if (Manager.getObject(arg).getClass().equals(Folder.class)){
              Folder currFolder = (Folder) Manager.getObject(arg);
              Vector childNames = currFolder.getAllChildrenNames();
              if (childNames != null){
                java.util.Collections.sort(childNames);
              }
              int i = 0;
              while (currFolder.getChildren(i) != null){
                contents = contents + "     " + childNames.get(i);
                i++;
              }
              contents = arg + ": " + contents + "\n";
            }
            else{
              contents = arg + "\n";
            }
            
        }
        else{
          Output.printPathError();
        }
      }
      else {
        Output.printError();
      }
      Output.printString(contents);
    }
  }
  
}
