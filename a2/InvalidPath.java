package a2;

public class InvalidPath extends Exception {

  public InvalidPath(String message, String path) {
    super(path + message);
  }

}
