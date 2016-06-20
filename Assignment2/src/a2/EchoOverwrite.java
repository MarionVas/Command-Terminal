package a2;

import java.util.Vector;

public class EchoOverwrite {
  private FileSystem fileSystem = new FileSystem();
  private int indexOfSymbol;
  private String parameter;
  private Folder currFolder = fileSystem.getCurrFolder();
  private Output output = new Output();

  public EchoOverwrite(String parameter) {
    this.parameter = parameter;
    this.indexOfSymbol = parameter.indexOf(">");
  }

  public void execute() {
    if (this.indexOfSymbol == -1) {
      // print parameter
    } else {
      String body = parameter.substring(0, indexOfSymbol - 1);
      String fileName = findFileName();
      File file = currFolder.getFile(fileName);
      if (file.equals(null)) {
        output.printFileNameError();
      } else {
        replace(file, body);
      }
    }
  }

  private String findFileName() {
    String fileName = this.parameter.substring(this.indexOfSymbol + 3,
        this.parameter.length());
    return fileName;
  }

  private void replace(File file, String body) {
    file.setBody(body);
  }

  public int getIndexOfSymbol() {
    return indexOfSymbol;
  }

  public String getParameter() {
    return parameter;
  }

}
