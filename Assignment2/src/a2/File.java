package a2;

import java.util.Vector;

public class File extends JFileSystem {
  private String name;
  private String body = "";

  /**
   * The constructor
   */

  public File(String name) {
    this.name = name;
  }

  /**
   * This method adds a given string to the body of the file
   * 
   * @param append - a string added to the body of the file
   */

  public void addToBody(String append) {
    this.body += append;
  }

  /**
   * This method changes the body of the file to the new given body
   * 
   * @param - a string that is the new body of the file
   */

  public void setBody(String body) {
    this.body = body;
  }

  /**
   * This method returns the name of the file
   */

  public String getName() {

    return this.name;
  }

  /**
   * This method returns the body of the file
   */

  public String getBody() {
    return this.body;
  }

}
