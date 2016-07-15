package a2;

import java.net.*;
import java.io.*;

public class Curl implements CommandInterface {
  private FileSystem fileSystem;
  // initialize a string variable for the path given as parameter
  private String[] parameter;
  private String URL;
  private final String stringToOutput = "";

  public Curl(JFileSystem manager, String[] parameter) {
    this.parameter = parameter;
    this.fileSystem = manager;
    this.URL = parameter[0];
  }

  public String execute() {
    try {
      URL webAddress = new URL(URL);
      BufferedReader in =
          new BufferedReader(new InputStreamReader(webAddress.openStream()));
      String inputLine;
      String fileName = URL.substring(URL.lastIndexOf("/") + 1);
      File file = new File(fileName);
      while ((inputLine = in.readLine()) != null)
        file.addToBody(inputLine + "\n");
      in.close();
    } catch (IOException e) {
      System.out.println("That was not a valid URL.");
    }
    return stringToOutput;
  }

  public String manual() {
    return null;
  }


}
