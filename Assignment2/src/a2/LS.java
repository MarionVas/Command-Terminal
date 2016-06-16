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
        contents = contents + currFolder.getChildrenName(i);
      }
    }
    return contents;
  }
}
