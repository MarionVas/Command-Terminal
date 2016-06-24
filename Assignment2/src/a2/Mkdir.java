package a2;

import java.util.*;

public class Mkdir implements CommandInterface {
  // The JFileSystem
  private FileSystem Manager;
  // The arguments that the user submits
  private String[] names;
  // List of all the special characters that cannot be used
  private String[] specialChar =
      new String[] {"/", "!", "@", "$", "&", "#", "*", "(", ")", "?", ":", "[",
          "]", "\"", "<", ">", "\'", "`", "\\", "|", "=", "{", "}", "/", ";"};

  /**
   * The constructor
   * 
   * @param fileManager - The JFileSystem with all the file and folder
   * @param arg - The string array with all the arguments the user enters
   */
  public Mkdir(JFileSystem fileManager, String[] arg) {
    this.Manager = fileManager;
    this.names = arg;

  }

  /**
   * Creates a directory based off of a absolute path (starting from "/" the
   * root)
   * 
   * @param name - the absolute path
   */
  public void executeFullPath(String name) {
    // The parent path (everything up to and excluding the last "/")
    String parentPath = name.substring(0, name.lastIndexOf("/"));
    // If the parent path is a valid existing path
    if (Manager.checkValidPath(parentPath)) {
      // Used to find the name of the directory
      int currDirIndex = name.lastIndexOf("/");
      // Getting the name of the directory
      String currDir = name.substring(currDirIndex + 1, name.length());
      // Creating the new folder with its name and its path
      Folder newFolder = new Folder(currDir, name);
      // Adding the full path to the FileSystem
      Manager.addFullPath(name);
      // Getting the parent folder form using the parent path
      Folder parentFolder = (Folder) Manager.getObject(parentPath);
      // Adding the new folder as a child folder to the parent
      parentFolder.addChildren(newFolder);
    } else if (parentPath.equals("")) { // If folder is being created at root
      int currDirIndex = name.lastIndexOf("/");
      String currDir = name.substring(currDirIndex + 1, name.length());
      Folder newFolder = new Folder(currDir, name);
      Manager.addFullPath(name);
      Folder parentFolder = (Folder) Manager.getObject("/");
      parentFolder.addChildren(newFolder);

    } else {
      // If it is an invalid parent path
      Output.printPathError();
    }
  }

  /**
   * Accepts a local path is ".." inside as part of it and returns the
   * corresponding full path
   * 
   * @param name - the argument from the user
   * @return The absolute path of the folder that the user specified
   */
  public String removeDots(String name) {
    // Counting starts from the beginning of the string and moves to the end
    int headIndex = 0;
    // The number of occurrences of the specified string
    int numOfOccurences = 0;
    // Loop goes until the end of the string
    while (headIndex != -1) {
      // The first index of ".." from start to endIndex
      headIndex = name.indexOf("..", headIndex);
      // If the specified string ("..") is still found within the above
      // substring
      if (headIndex != -1) {
        // Increment the number of occurrences
        numOfOccurences++;
        // Increasing the search by 2, (since ".." has a length of 2)
        headIndex += 2;
      }
    }
    // Getting the current working (local) path
    String path = Manager.getCurrPath();
    int indexDots = name.indexOf("/");
    // Runs for the number of times ".." is in the argument
    for (int i = 0; i < numOfOccurences; i++) {
      // If name still contains ".." and but does not start with a ".." then
      // name is in the form ../DIR_NAME/../ , so the DIR_NAME must be added
      // to the absolute path and "i" must be decremented
      if (!name.startsWith("..") && name.contains("..")) {
        path = path + "/" + name.substring(0, indexDots);
        i--;
      } else {
        try { // If the number of ".." reaches past the root and error is thrown
          // Cutting a section of the current path off
          path = path.substring(0, path.lastIndexOf("/"));
        } catch (Exception e) {
          // Returning an invalid path
          path = "//////////";
          break;
        }
      }
      // Removing a piece of the argument off
      name = name.substring(indexDots + 1, name.length());
      indexDots = name.indexOf("/");

    }
    // Returning the absolute path
    return path + "/" + name;
  }

  /**
   * If only a directory name is given
   * 
   * @param name - the argument the user enters
   */
  public void executeLocal(String name) {
    // Determines whether or not the name is valid or not
    boolean valid = true;
    // Checks if any invalid characters are present in the string
    for (int i = 0; i < specialChar.length; i++) {
      if (name.contains(specialChar[i])) {
        valid = false;
      }
    }
    if (valid) {
      String fullPath = "";
      // Creating a full path based off the name argument
      // If the working directory is at root
      if (Manager.getCurrPath().equals("/")) {
        fullPath = Manager.getCurrPath() + name;
      } else {
        fullPath = Manager.getCurrPath() + "/" + name;
      }
      // Creation of the folder and adding it to the parent path
      Folder newFolder = new Folder(name, fullPath);
      Manager.addFullPath(fullPath);
      Folder parentFolder = (Folder) Manager.getObject(Manager.getCurrPath());
      parentFolder.addChildren(newFolder);
    } else { // If and invalid name is entered
      Output.printFileNameError();
    }
  }

  /**
   * The method that will be called by ProQuery. Checks which kind of creation
   * is required and runs the appropriate method. Runs all arguments provided
   * Also print errors if the directory already exists
   */
  public void execute() {
    // Runs for all arguments in the string array
    for (int index = 0; index < this.names.length; index++) {
      // Current argument
      String name = this.names[index];
      // Removing any "/" that is present at the end
      if (name.endsWith("/")) {
        name = name.substring(0, name.length() - 1);
      }
      // Since the "." operator does not really do anything significant it can
      // be removed from the path at it should still be equivalent to if the 
      // "." was not there
      if (name.contains("/./") || name.endsWith("/.") || name.startsWith("/.")){
        if (name.startsWith("./")){
          name = name.substring(2, name.length());
        }
        CharSequence operator = "/./";
        while (name.contains(operator)){
          name = name.replace(operator, "/");
        }
        if (name.endsWith("/.")){
          name = name.substring(0, name.length()-2);
        }
      }
      // If an absolute path is given
      if (name.startsWith("/")) {
        // If the directory already exists
        if (Manager.checkValidPath(name)) {
          Output.printError();
        } else {
          this.executeFullPath(name);
        }
      } // If a local path with ".."'s is given
      else if (name.contains("..")) {
        // Removing the ".." and building an absolute path
        name = this.removeDots(name);
        if (Manager.checkValidPath(name)) {
          Output.printError();
        } else {
          this.executeFullPath(name);
        }
      } // If a local path is given
      else if (name.contains("/")) {
        // Building an absolute path
        if (Manager.getCurrPath().equals("/")) {
          name = "/" + name;
        } else {
          name = Manager.getCurrPath() + "/" + name;
        }

        if (Manager.checkValidPath(name)) {
          Output.printError();
        } else {
          this.executeFullPath(name);
        }

      } // If the argument is only a directory name
      else {
        // Check if the name already exists
        if (Manager.checkValidPath(name)) {
          Output.printError();
        } else {
          this.executeLocal(name);
        }
      }
    }
  }

}
