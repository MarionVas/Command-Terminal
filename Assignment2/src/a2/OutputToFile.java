package a2;

import a2.File;
import a2.Folder;
import a2.JFileSystem;
import a2.Output;

abstract class OutputToFile {
  private String[] specialChar = new String[] {"/", "!", "@", "$", "&", "#",
      "*", "(", ")", "?", ":", "[", "]", "\"", "<", ">", "\'", "`", "\\", "|",
      "=", "{", "}", "/", ";", " "};

  /**
   * This method will replace the contents of the outfile given to the
   * parameters of the method with the String given to the parameter
   * 
   * @param fileSystem - The JFileSystem with all the file and folder
   * @param output - the string that will replace the body of the outfile
   * @param outfile - the file that will have its contents replaced by the
   *        output
   */

  private void overWrite(JFileSystem fileSystem, String output,
      String outfile) {
    // find the file that the user is going to be working with
    File replaceFile = findFile(fileSystem, outfile);
    if (replaceFile == null) {
      Output.printFileNameError();
    } else {
      // replace the contents of the file with the String
      replace(replaceFile, output);
    }
  }

  /**
   * This method will add a line to the end of a outfile given to the parameters
   * of the method with the String given to the parameter.
   * 
   * @param fileSystem - The JFileSystem with all the file and folder
   * @param output - the string that will be added to the body of the outfile
   * @param outfile - the file that will have the output added to its contents
   */

  private void append(JFileSystem fileSystem, String output, String outfile) {
    // run the super class' fileFile method to get the file to work with
    File appendFile = findFile(fileSystem, outfile);
    if (appendFile == null) {
      Output.printFileNameError();
    } else {
      // add the String to the file instead of replacing the file's content
      append(appendFile, output);
    }
  }

  /**
   * This method will check if the outfile given is a valid file name or a path.
   * If the outfile given is a path, this method will traverse the path and find
   * the file. If an invalid file is given or an invalid path is given null will
   * be returned.
   * 
   * @param fileSystem - The JFileSystem with all the file and folder
   * @param outfile - the file that is requested
   * 
   * @return file - the File matching of the outfile given to the parameters
   */

  private File findFile(JFileSystem fileSystem, String outfile) {
    // get the current working directory
    Folder currFolder = fileSystem.getCurrFolder();
    String currPath = fileSystem.getCurrPath();
    // initialize a variable to hold the return
    File file;
    // initialize a variable to check if the outfile is a valid file name
    boolean valid = true;
    // loop through each character of the file name
    for (String eachChar : specialChar) {
      // check if file name contains any illegal characters
      if (outfile.contains(eachChar)) {
        // if the file name contains illegal characters falsify the valid
        // variable
        valid = false;
      }
    }
    // check if the outfile is a valid file name
    if (valid) {
      // call the helper function to get the file
      file = findFileHelper(currFolder, outfile);
      // add the path to the file to the fileSystem
      if (fileSystem.getCurrPath().equals("/")) {
        fileSystem.addFullPath(fileSystem.getCurrPath() + outfile);
      } else {
        fileSystem.addFullPath(fileSystem.getCurrPath() + "/" + outfile);

      }
      // check if the outfile is a path
    } else if (outfile.contains("/")) {
      // find the path to the folder where the file is kept
      String outfileLocation = outfile.substring(0, outfile.lastIndexOf("/"));
      // check if the path is the root and not a valid file name
      if (outfileLocation.equals("")) {
        // if the outfile is the root return null
        file = null;
        // check if the path to the parents folder is a valid path
      } else if (fileSystem.checkValidPath(outfileLocation)) {
        // create a string array to hold the path
        String[] location = {outfileLocation};
        // create a cd object to traverse the path
        CD cd = new CD(fileSystem, location);
        // traverse the path
        cd.execute();
        // get the new current folder after traversing the path
        Folder newCurrFolder = fileSystem.getCurrFolder();
        // call the helper function to get the file
        file = findFileHelper(currFolder, outfile);
        // add the path to the file to the fileSystem
        if (fileSystem.getCurrPath().equals("/")) {
          fileSystem.addFullPath(fileSystem.getCurrPath() + outfile);
        } else {
          fileSystem.addFullPath(fileSystem.getCurrPath() + "/" + outfile);
        }
        // traverse back to the original folder
        fileSystem.setCurrFolder(currFolder);
        fileSystem.setFullPath(currPath);
        // if the path given to the folder is not a valid path return null
      } else {
        file = null;
      }
      // if the file name is not a valid file name nor a path return null
    } else {
      file = null;
    }
    // return the file
    return file;
  }

  /**
   * Given a folder, this method will return a file that is given to the
   * parameters that exists in the folder or will create a new file if the file
   * does not exist in the folder provided. This method will then return the
   * file
   * 
   * @param fileSystem - The JFileSystem with all the file and folder
   * @param outfile - the file that is requested
   * 
   * @return file - the File matching of the outfile given to the parameters
   */


  private File findFileHelper(Folder currFolder, String outfile) {
    // call the getFile method to get the file
    File file = currFolder.getFile(outfile);
    // check if the file exists
    if (file == null) {
      // if the file does not exist make a new file
      file = new File(outfile);
      // add the file to the current working directory
      currFolder.addChildren(file);
    }
    return file;
  }


  /**
   * This method will replace the body of the file with the string provided in
   * the parameter given to the constructor
   * 
   * @param file - the file that will have its contents replaced by the output
   * @param body - the string that will replace the body of the file
   */

  private void replace(File file, String body) {
    file.setBody(body);
  }

  /**
   * This method will add a given line to the end of the given file
   * 
   * @param file - the file that will have the output added to its contents
   * @param body - the string that will be added to the body of the file
   */

  private void append(File file, String body) {
    file.addToBody(body + "\n");
  }
}
