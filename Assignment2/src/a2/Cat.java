package a2;

public class Cat implements CommandInterface { // require execute() method
  private FileSystem fileSystem;
  private String[] fileNames;

  public Cat(JFileSystem manager, String[] files) {
    this.fileSystem = manager;
    this.fileNames = files;
  }



  public void execute() {
    Folder currFolder = fileSystem.getCurrFolder();
    if (this.fileNames.length == 1) {
      File file = (File) currFolder.getFile(fileNames[0]);
      Output.printContents(file.getBody());
    } else {
      for (String eachFile : this.fileNames) {
        File file = (File) currFolder.getFile(eachFile);
        Output.printContents(file.getBody());
        Output.printString("");
        Output.printString("");
        Output.printString("");
      }

    }
  }

}
