package a2;

public class PWD implements CommandInterface{
  private FileSystem fileSystem;
  private String path;
  public PWD(JFileSystem manager){
    this.fileSystem = manager;
  }
  public void execute() {
    Output.printString(fileSystem.getCurrPath());
  }
}
