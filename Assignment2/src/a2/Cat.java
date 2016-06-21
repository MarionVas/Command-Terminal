package a2;

public class Cat implements CommandInterface { // require execute() method
  private FileSystem fileSystem;
  private String[] fileNames;

  public Cat (JFileSystem manager, String[] files){
    this.fileSystem = manager;
    this.fileNames = files;
  }



  public void execute(){
    Folder currFolder = fileSystem.getCurrFolder();
    for(String eachFile: this.fileNames){
      File file = (File) currFolder.getFile(eachFile);
      Output.printContents(file.getBody());
      Output.printString("");
      Output.printString("");
      Output.printString("");
    }
  }

}
