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
  private String fileOriginalArg = "";
  private String stringToOutput = "";
  private String stringToOutputTest = "";
  private Boolean recursive = false;

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
   * Used to get the output of the command
   * 
   * @return stringToOutput
   */
  public String getStringToOutput() {
    return this.stringToOutputTest;
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
      childNames = currFolder.getChildrenNames();
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
          contents = "That was not a valid path.\n";
        } else {
          // All the children in the specified folder
          Vector childNames = currFolder.getChildrenNames();
          if (childNames != null) {
            // Sorting them alphabetically
            java.util.Collections.sort(childNames);
          }
          int i = 0;
          // Adding the child names to a string and formating it
          if (childNames != null) {
            while (i < currFolder.getChildren().size()) {
              contents = contents + "     " + childNames.get(i);
              i++;
            }
          }
          // Finishing up the formating
          if (!this.recursive) {
            contents = this.fileOriginalArg + ": " + contents + "\n";
          } else {
            contents = "\n" + currFolder.getPath() + ": " + contents + "\n";
          }
        }
      } else {
        // If the path specified is to a file
        // If there must not be a slash at the end of the file name
        if (slashAtEnd) {
          contents = this.fileOriginalArg + "\n";
        } else {
          contents = "That was not a valid path.\n";
        }
      }
    } else {
      // If an incorrect path is given
      contents = "That was not a valid path.\n";
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
    if (name.equals("..") && !path.equals("")) {
      return path;
    } else if (path.equals("") && name.equals("..")) {
      return "/";
    }
    return path + "/" + name;
  }

  public String removeSingleDot(String arg) {
    if (arg.contains("/./") || arg.endsWith("/.") || arg.startsWith("./")
        || arg.equals(".")) {
      // If the current directory is specified
      if (arg.equals("./") || arg.equals(".")) {
        arg = this.Manager.getCurrPath();
      } else {
        if (arg.startsWith("./")) {
          arg = arg.substring(2, arg.length());
        }
        CharSequence operator = "/./";
        while (arg.contains(operator)) {
          arg = arg.replace(operator, "/");
        }

        if (arg.endsWith("/.") && !arg.equals("/.")) {
          arg = arg.substring(0, arg.length() - 2);
        } else if (arg.endsWith("/.") && arg.equals("/.")) {
          arg = "/";
        }
      }

    }
    return arg;
  }

  public String recurseLS(int childIndex, Item dirrOrFile) {
    // If the end of the subtree is reached
    String result = "";
    if (dirrOrFile == null || dirrOrFile.equals(File.class)) {
      result = "";
    } else {
      Vector<Item> allChildren = dirrOrFile.getChildren();
      if (allChildren != null && allChildren.size() != 0) {
        if (childIndex == 0) {
          result = this.executeFullPath(((Folder) dirrOrFile).getPath(), true)
              + this.recurseLS(childIndex, allChildren.get(childIndex))
              + this.recurseLS(childIndex + 1, dirrOrFile);
        } else if (childIndex < allChildren.size()) {
          result = this.recurseLS(0, allChildren.get(childIndex))
              + this.recurseLS(childIndex + 1, dirrOrFile);
        }
      } else {
        result = this.executeFullPath(((Folder) dirrOrFile).getPath(), true);
      }
    }

    return result;
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
      this.fileOriginalArg = args[indexarg];
      if (this.arg.equals("-r") || this.arg.equals("-R")) {
        indexarg++;
        if (args.length != 1){
          this.arg = args[indexarg];
          this.fileOriginalArg = args[indexarg];
        }
        else{
          this.arg = "";
          this.fileOriginalArg = "";
        }
        this.recursive = true;
      }
      this.stringToOutput = "";
      if (!arg.equals("")){
        try {
          arg = this.Manager.getFullPath(arg);
        } catch (InvalidPath e) {
          System.err.println(e);
        }
      }
      if (arg == "") {
        slashAtEnd = true;
        if (!this.recursive) {
          this.stringToOutput += executeNoArg();
        } else {
          this.stringToOutput += recurseLS(0, this.Manager.getCurrFolder());
        }
      } 
      else {
        if (!this.recursive) {
          this.stringToOutput += executeFullPath(arg, slashAtEnd);
        } else {
          this.stringToOutput += recurseLS(0, this.Manager.getObject(arg));
        }
      } 
      // Using the output class to print the string
      if (!this.stringToOutput.endsWith("\n")) {
        this.stringToOutputTest += this.stringToOutput + "\n";
      } else {
        this.stringToOutputTest += this.stringToOutput;
      }

      Output.printString(this.stringToOutput);
    }
  }

  public String manual() {
    return "ls [PATH  …] - Print the contents of the specified files or\n"
        + "directories. If no path is given, print the contents of the\n"
        + "current file or directory.\n";
  }

}
