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
          Folder currFolder = (Folder) Manager.getObject(Manager.getCurrPath());
          Vector childNames;
          if (currFolder == null){
            childNames = null;
          }
          else{
            childNames = currFolder.getAllChildrenNames();
          }
          
          if (childNames != null){
            java.util.Collections.sort(childNames);
          }
          int i = 0;
          if (childNames != null){
             while (i < childNames.size()){
              contents = contents + "\n" + childNames.get(i);
              i++;
             }
          }
        
      }
      else if (arg.startsWith("/")){
        if (Manager.checkValidPath(arg)){
  
            if (Manager.getObject(arg).getClass().equals(Folder.class)){
              
              Folder currFolder = (Folder) Manager.getObject(arg);
              Vector childNames = currFolder.getAllChildrenNames();
              if (childNames != null){
                java.util.Collections.sort(childNames);
              }
              int i = 0;
              while (i < currFolder.getAllChildren().size()){
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
          Output.printPathError();
        }
      }
      else if (arg.startsWith("..")){
        if (arg.equals("..") || arg.equals("../")){
          String path = "";
          if (Manager.getCurrPath().split("/").length > 2){
            for (int i = 1; i < Manager.getCurrPath().split("/").length - 1; i++){
              path =  path + "/" +  Manager.getCurrPath().split("/")[i];
            }
            if (Manager.checkValidPath(arg)){
                if (Manager.getObject(arg).getClass().equals(Folder.class)){
                  Folder currFolder = (Folder) Manager.getObject(arg);
                  Vector childNames = currFolder.getAllChildrenNames();
                  if (childNames != null){
                    java.util.Collections.sort(childNames);
                  }
                  int i = 0;
                  while (i < currFolder.getAllChildren().size()){
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
              Output.printPathError();
            }
          }
          else if (Manager.getCurrPath().equals("/")){
            Output.printError();
          }
        }
        else{
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
              if (Manager.getObject(arg).getClass().equals(Folder.class)){
                Folder currFolder = (Folder) Manager.getObject(arg);
                Vector childNames = currFolder.getAllChildrenNames();
                if (childNames != null){
                  java.util.Collections.sort(childNames);
                }
                int i = 0;
                while (i < currFolder.getAllChildren().size()){
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
            Output.printPathError();
          }
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
              while (i < currFolder.getAllChildren().size()){
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
       if (Manager.getCurrPath().equals("/")){
         arg = Manager.getCurrPath() + arg;
       }
       else {
         arg = Manager.getCurrPath() + "/" + arg;
       }
       
       if (Manager.checkValidPath(arg)){
           //String argPath = Manager.getCurrPath() + "/" + arg;
           if (Manager.getObject(arg).getClass().equals(Folder.class)){
             
             Folder currFolder = (Folder) Manager.getObject(arg);
             Vector childNames = currFolder.getAllChildrenNames();
             if (childNames != null){
               java.util.Collections.sort(childNames);
             }
             int i = 0;
             while (i < currFolder.getAllChildren().size()){
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
         Output.printPathError();
       }
      }
      Output.printString(contents);
    }
  }
  
}
