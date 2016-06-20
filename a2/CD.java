package a2;

public class CD {
  private FileSystem fileSystem = new FileSystem();
  private Output output = new Output();
  private String path;

  public CD (String path) {
    this.path = path;
    }

  public void execute() {
    String currPath = fileSystem.getCurrPath();
    if (this.path == "..") {
      if (currPath == "/") {
        output.printPathError();
      } else {
        currPath = currPath.substring(0, currPath.lastIndexOf("/"));
        fileSystem.setFullPath(currPath);
        fileSystem.setCurrFolder((Folder) fileSystem.getObject(currPath));
      }
    } else if (this.path == ".") {
      // do nothing
    } else {
      boolean correctPath = fileSystem.checkValidPath(this.path);
      if (!correctPath) {
        output.printPathError();
      } else {
        fileSystem.setFullPath(this.path);
        fileSystem.setCurrFolder((Folder) fileSystem.getObject(this.path));

      }
    }
  }

}
