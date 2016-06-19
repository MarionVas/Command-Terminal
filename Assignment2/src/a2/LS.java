package a2;

public class LS {
  private FileSystem Manager;
  public LS(FileSystem fileManager){
    this.Manager = fileManager;
  }
  public String execute(String arg){
    String contents = "";
    if (arg == ""){
      Folder currFolder = (Folder) Manager.getObject(Manager.getCurrPath());
      int i = 0;
      while (currFolder.getChildren(i) != null){
        contents = contents + "\n" + currFolder.getChildrenName(i);
      }
    }
    else if (arg.startsWith("/")){
      if (Manager.checkValidPath(arg)){
        Folder currFolder = (Folder) Manager.getObject(arg);
        int i = 0;
        while (currFolder.getChildren(i) != null){
          contents = contents + "\n" + currFolder.getChildrenName(i);
        }
        contents = arg + ": " + contents + "\n";
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
        Folder currFolder = (Folder) Manager.getObject(arg);
        int i = 0;
        while (currFolder.getChildren(i) != null){
          contents = contents + "\n" + currFolder.getChildrenName(i);
        }
        contents = arg + ": " + contents + "\n";
      }
      else{
        //Print error
      }
    }
    else if (arg.contains("/")){
      arg = Manager.getCurrPath() + "/" + arg;
      if (Manager.checkValidPath(arg)){
        Folder currFolder = (Folder) Manager.getObject(arg);
        int i = 0;
        while (currFolder.getChildren(i) != null){
          contents = contents + "\n" + currFolder.getChildrenName(i);
        }
        contents = arg + ": " + contents + "\n";
      }
      else{
        //Print error
      }
    }
    else {
      
    }
    return contents;
  }
  
}
