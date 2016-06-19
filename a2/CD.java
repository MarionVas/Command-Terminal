package a2;

public class CD {
  private FileSystem fileSystem = new FileSystem();

  public void execute(String path) {
    String currPath = fileSystem.getCurrPath();
    if (path == "..") {
      if (currPath == "/") {
        // print Error
      } else {
        currPath = currPath.substring(0, currPath.lastIndexOf("/") + 1);
        fileSystem.setFullPath(currPath);
        String lastFolder =
            currPath.substring(currPath.lastIndexOf("/"), currPath.length());
        fileSystem.setCurrFolder((Folder) fileSystem.getObject(lastFolder));
      }
    } else if (path == ".") {
      // do nothing
    } else {
      boolean correctPath = fileSystem.relativePathChecker(path);
      if (!correctPath) {
        // print Error
      } else {
        fileSystem.setFullPath(path);
        String lastFolder =
            path.substring(path.lastIndexOf("/"), path.length());
        fileSystem.setCurrFolder((Folder) fileSystem.getObject(lastFolder));

      }
    }
  }

}
