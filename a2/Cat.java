package a2;

import java.util.Arrays;
import java.util.Vector;

public class Cat implements CommandInterface {
  private FileSystem fileSystem;
  private String[] fileNames;
  private String stringToOutput = "";

  /**
   * The constructor
   * 
   * @param manager - The JFileSystem with all the file and folder
   * @param files - The string array with all the file names the user enters
   */

  public Cat(JFileSystem manager, String[] files) {
    this.fileSystem = manager;
    this.fileNames = files;
  }

  /**
   * This method will take the parameter passed into the constructor and read
   * the contents of those files in the current directory. If the files do not
   * exist, an error message will be printed
   */

  public String execute() {
    // get the current working directory
    Folder currFolder = fileSystem.getCurrFolder();
    // check if the user wants to read one file
    if (this.fileNames.length == 1) {
      // get the file the user wants to read
      File file = (File) currFolder.getFile(fileNames[0]);
      // if the file does not exist print an error message
      if (file == null) {
        if (fileNames[0].contains("/")) {
          String path = fileNames[0];
          if (path.lastIndexOf("/") == path.length() - 1) {
            path = fileNames[0].substring(0, fileNames[0].lastIndexOf("/"));
          }
          if (path.equals("")) {
            System.out.println("That was not a valid path");
          } else {
            file = getFileFromPath(path);
            if (file == null) {
              System.out.println("That was not a valid path or file name.");
            } else {
              stringToOutput = file.getBody();
            }
          }
        } else {
          System.out.println("That was not a valid path or file name.");
        }
      } else {
        stringToOutput = file.getBody();
      }
    } else {
      // get each file the user wants to print
      if (!(Arrays.asList(fileNames).contains(">")
          | Arrays.asList(fileNames).contains(">>"))){
        for (String eachFile : this.fileNames) {
          // get the file the user wants to read
          File file = (File) currFolder.getFile(eachFile);
          if (file == null) {
            if (eachFile.contains("/")) {
              String path = fileNames[0];
              if (path.lastIndexOf("/") == path.length() - 1) {
                path = fileNames[0].substring(0, fileNames[0].lastIndexOf("/"));
              }
              if (path.equals("")) {
                System.out.println("That was not a valid path");
              } else {
                file = getFileFromPath(path);
                if (file == null) {
                  System.out.println("That was not a valid path or file name.");
                } else {
                  stringToOutput = file.getBody() + "\n\n\n\n";
                }
              }
            } else {
              System.out.println("That was not a valid path or file name.");
            }
          } else {
            // if the file does exist print the contents of the file
            // print three lines to separate each file being read
            stringToOutput += file.getBody() + "\n\n\n\n";
          }
        }
      }
    }
    return stringToOutput;
  }


  private File getFileFromPath(String path) {
    File file = null;
    try {
      path = fileNames[0].substring(0, fileNames[0].lastIndexOf("/"));
      String fileName =
          fileNames[0].substring(fileNames[0].lastIndexOf("/") + 1);
      String fullPath = fileSystem.getFullPath(path);
      Folder fileFolder =
          (Folder) fileSystem.getObject(fileSystem.getFullPath(fullPath));
      file = (File) fileFolder.getFile(fileNames[0]);
    } catch (InvalidPath e) {
    }
    return file;
  }


  /**
   * This function returns the instructions on how to use the command cat.
   * 
   * @return a string telling users the how the command works
   */

  public String manual() {
    return "cat FILE1 [FILE2  …] - Displays the contents of the specified\n"
        + "files concatenated within the shell.\n";
  }
}
