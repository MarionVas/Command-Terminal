package a2;

import java.awt.Component;
import java.awt.List;
import java.util.Vector;

public class LS implements CommandInterface {
  // The jFileSystem
  private FileSystem Manager;
  // The arguments that the user enters
  private String[] args;
  private String arg = "";

  /**
   * The constructor, taking ins a JFileSystem and a string array of arguments
   * 
   * @param fileManager - The JFilemMnager with all the directories and files
   * @param name - The argument(s)
   */
  public LS(JFileSystem fileManager, String[] name) {
    this.Manager = fileManager;
    this.args = name;
  }

  /**
   * Secondary constructor if no arguments are given
   * 
   * @param fileManager
   */
  public LS(JFileSystem fileManager) {
    this.Manager = fileManager;
    this.args = new String[1];
    // Setting a blank command
    this.args[0] = "";
  }

  /**
   * A method responsible for displaying the contents of the local directory
   * Prints each of the contents on a newline (as per A2a hand out)
   * 
   * @return contents - Returns a string containing the contents
   */
  public String executeNoArg() {
    String contents = "";
    // Getting the current folder
    Folder currFolder = (Folder) Manager.getObject(Manager.getCurrPath());
    // A vector representing all the names of the children directories to this
    // one
    Vector childNames;
    // If there are no children
    if (currFolder == null) {
      childNames = null;
    } else {
      // Getting the string vector of all the children stored in the folder
      childNames = currFolder.getAllChildrenNames();
    }
    // Sorts the child names in alphabetical order
    if (childNames != null) {
      java.util.Collections.sort(childNames);
    }
    int i = 0;
    if (childNames != null) {
      // Adding each of the children names to a new string
      while (i < childNames.size()) {
        contents = contents + "\n" + childNames.get(i);
        i++;
      }
    }
    return contents;
  }

  /**
   * A method responsible for displaying the contents of a directory or file at
   * the given path
   * 
   * @param arg - The absolute path
   * @return contents - A string representation of all the children
   */
  public String executeFullPath(String arg, boolean slashAtEnd) {
    String contents = "";
    // Checking if the path given is valid
    if (Manager.checkValidPath(arg)) {
      // Determining the type of object that has been specified
      if (Manager.getObject(arg).getClass().equals(Folder.class)) {
        // Getting the current folder
        Folder currFolder = (Folder) Manager.getObject(arg);
        // If the folder does not exist
        if (currFolder.equals(null)) {
          Output.printPathError();
        } else {
          // All the children in the specified folder
          Vector childNames = currFolder.getAllChildrenNames();
          if (childNames != null) {
            // Sorting them alphabetically
            java.util.Collections.sort(childNames);
          }
          int i = 0;
          // Adding the child names to a string and formating it
          while (i < currFolder.getAllChildren().size()) {
            contents = contents + "     " + childNames.get(i);
            i++;
          }
          // Finishing up the formating
          contents = arg + ": " + contents + "\n";
        }
      } else {
        // If the path specified is to a file
        // If there must not be a slash at the end of the file name
        if (!slashAtEnd){
          contents = arg + "\n";
        }
        else{
          Output.printPathError();
        }
      }
    } else {
      // If an incorrect path is given
      Output.printPathError();
    }
    return contents;
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
   * The method that will be called by ProQuery. Determines what kind of
   * argument the user has entered; Runs all the arguments that the user has
   * entered
   */
  public void execute() {
    // Runs all elements in the string array
    for (int indexarg = 0; indexarg < this.args.length; indexarg++) {
      boolean slashAtEnd = false;
      this.arg = args[indexarg];
      // Removing the slash at the end if one exists
      if (arg.endsWith("/")) {
        slashAtEnd = true;
        arg = arg.substring(0, arg.length() - 1);
      }
      // String representation of the children
      String contents = "";
      // If no argument was given
      if (arg == "") {
        contents = executeNoArg();
      } // If an absolute path was given
      else if (arg.startsWith("/")) {
        contents = executeFullPath(arg, slashAtEnd);
      } // If a path containing ".." was given
      else if (arg.contains("..")) {
        // Turning arg into an absolute path
        arg = this.removeDots(arg);
        contents = this.executeFullPath(arg, slashAtEnd);
      } // If a local path is given
      else if (arg.contains("/")) {
        // Creating an absolute path
        if (!Manager.getCurrPath().equals("/")) {
          arg = Manager.getCurrPath() + "/" + arg;
        } else {
          arg = "/" + arg;
        }
        contents = this.executeFullPath(arg, slashAtEnd);
      } // If a directory name is given
      else {
        // Building the absolute path
        if (Manager.getCurrPath().equals("/")) {
          arg = Manager.getCurrPath() + arg;
        } else {
          arg = Manager.getCurrPath() + "/" + arg;
        }
        contents = this.executeFullPath(arg, slashAtEnd);
      }
      // Using the output class to print the string
      Output.printString(contents);
    }
  }

}
