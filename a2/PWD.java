package a2;

public class PWD implements CommandInterface{
  private FileSystem fileSystem = new FileSystem();

  public String execute(String path) {
    return fileSystem.getCurrPath();
  }
}
