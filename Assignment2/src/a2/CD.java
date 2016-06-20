package a2;

public class CD {
  private FileSystem fileSystem = new FileSystem();
  private Output output = new Output();

  public void execute(String path) {
    String currPath = fileSystem.getCurrPath();
    if (path == "..") {
      if (currPath == "/") {
        output.printPathError();
      } else {
        currPath = currPath.substring(0, currPath.lastIndexOf("/"));
        fileSystem.setFullPath(currPath);
        fileSystem.setCurrFolder((Folder) fileSystem.getObject(currPath));
      }
    } else if (path == ".") {
      // do nothing
    } else {
      boolean correctPath = fileSystem.checkValidPath(path);
      if (!correctPath) {
        output.printPathError();
      } else {
        fileSystem.setFullPath(path);
        fileSystem.setCurrFolder((Folder) fileSystem.getObject(path));

      }
    }
  }

}
