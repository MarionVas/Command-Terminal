package a2;

abstract class OutputToFile {
  // collaboration with fileSystem
  private FileSystem fileSystem; // del equals
  // initialize a variable to hold the index of ">"
  private String[] specialChar = new String[] {"/", "!", "@", "$", "&", "#",
      "*", "(", ")", "?", ":", "[", "]", "\"", "<", ">", "\'", "`", "\\", "|",
      "=", "{", "}", "/", ";", " "};

  // initialize a variable to hold the parameter passed to the command
  private String[] parameter;
  // initialize a variable to hold the String passed to the command
  private String string;

  /**
   * The constructor
   * 
   * @param jFileSystem - The JFileSystem with all the file and folder
   * @param parameter - The string array with all the arguments the user enters
   */

  public OutputToFile(JFileSystem manager, String[] parameter) {
    this.fileSystem = manager;
    this.parameter = parameter;
    this.string = parameter[0].substring(1, parameter[0].length() - 1);
  }

  /**
   * This method will replace the contents of the outfile given in the
   * parameters of the constructor with the String given in the parameter
   */

  public void overWrite() {
    // find the file that the user is going to be working with
    File replaceFile = findFile();
    if (isFileNull()) {
      Output.printFileNameError();
    } else {
      // replace the contents of the file with the String
      replace(replaceFile, string);
    }
  }

  /**
   * This method will override the parent's execute method so that it adds a
   * line to the end of a file if an file exists instead of replacing the file
   * with the String passed into the parameter
   */

  public void append() {
    // run the super class' fileFile method to get the file to work with
    File appendFile = findFile();
    if (isFileNull()) {
      Output.printFileNameError();
    } else {
      // add the String to the file instead of replacing the file's content
      append(appendFile, string);
    }
  }

  /**
   * This method will returns the outFile given in the parameters of the
   * constructor, if the outfile specified does not exist, it will create a file
   * with the name of the outfile and return the newly created file
   */

  public File findFile() {
    // get the current working directory
    Folder currFolder = fileSystem.getCurrFolder();
    // find the name of the outfile
    String fileName = parameter[2];
    // get the outfile
    File file;
    boolean valid = true;
    for (String eachChar : specialChar) {
      if (fileName.contains(eachChar)) {
        valid = false;
      }
    }
    if (valid) {
      file = currFolder.getFile(fileName);
      // check if the file exists
      if (file == null) {
        // if the file does not exist make a new file
        file = new File(fileName);
        // add the file to the current working directory
        currFolder.addChildren(file);
        if (fileSystem.getCurrPath().equals("/")) {
          fileSystem.addFullPath(fileSystem.getCurrPath() + parameter[2]);
        } else {
          fileSystem.addFullPath(fileSystem.getCurrPath() + "/" + parameter[2]);
        }
      }
    } else {
      file = null;
    }
    // return the file
    return file;
  }


  /**
   * This method will replace the body of the file with the string provided in
   * the parameter given to the constructor
   */

  private void replace(File file, String body) {
    file.setBody(body);
  }

  /**
   * This method will add a given line to the end of the given file
   * 
   * @param file - the outfile given in the parameter
   * @param body - the String given in the parameter
   */

  private void append(File file, String body) {
    file.addToBody(body);
  }



  /**
   * This method returns the parameter provided to the constructor
   * 
   * @return parameter - The parameter provided to the constructor
   */

  /**
   * This method returns the String provided in the parameter provided to the
   * constructor
   * 
   * @return parameter - The parameter provided to the constructor
   */

  /**
   * This function return if no files are created because of an invalid name
   * 
   * @return file == null a boolean to check if a file is created
   */

  public boolean isFileNull() {
    File file = findFile();
    return file == null;
  }
}
