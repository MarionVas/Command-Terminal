package a2;

public class PWD implements CommandInterface {
  private FileSystem fileSystem;
  private String path;
  private String stringToOutput;

  public PWD(JFileSystem manager) {
    this.fileSystem = manager;
  }

  public void execute() {
    stringToOutput = fileSystem.getCurrPath();
    Output.printString(stringToOutput);
  }

  public String manual() {
    return "pwd - Prints the current working directory with its whole path.\n";
  }

  public String getStringToOutput() {
    return stringToOutput;
  }
}
