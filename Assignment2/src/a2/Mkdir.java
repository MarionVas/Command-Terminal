package a2;

public class Mkdir {
  private FileSystem Manager;
  
  public Mkdir(FileSystem fileManager){
    this.Manager = fileManager;
  }
  public void create(String name){
    if (name.startsWith("/")){
      int currDirIndex = name.lastIndexOf("/");
      String currDir = name.substring(currDirIndex+1, name.length());
      Folder newFolder = new Folder(currDir, name);
      Manager.add(newFolder);
      System.out.println(currDir + "    " +  name);
    }
    else{
      String fullPath = Manager.getCurrPath();
      Folder newFolder = new Folder(name, fullPath);
      Manager.add(newFolder);
    }
  }
}
