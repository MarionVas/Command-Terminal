package a2;

public class PWD implements CommandInterface{
  private FileSystem fileSystem;
  private String path;
  public PWD(JFileSystem manager, String arg){
    this.fileSystem = manager;
    this.path = arg;
  }
  public String execute(String path) {
    return fileSystem.getCurrPath();
  }
}
