package a2;

public class PWD {
  private FileSystem fileSystem = new FileSystem();

  public String execute(String path) {
    return fileSystem.getCurrPath();
  }
}
