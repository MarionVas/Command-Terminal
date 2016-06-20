package a2;

import java.util.Vector;

public class Output {

  /**
   * This function display an error message claiming the command is invalid, if
   * an invalid command is given
   * 
   */

  public static void printError() {
    System.out.println("That was not a recognized command.");
  }

  /**
   * This function display an error message claiming the path is invalid, if an
   * invalid path is given
   * 
   */

  public static void printPathError() {
    System.out.println("That was not a valid path.");
  }

  /**
   * This function display an error message claiming the file name is invalid,
   * if an invalid file name is given
   * 
   */

  public static void printFileNameError() {
    System.out.println("That was not a valid file name.");
  }


  /**
   * This function display a given string
   * 
   */


  public static void printString(String contents) {
    System.out.println(contents);
  }


  /**
   * This function display the contents of a given string vector with each
   * element on a new line
   * 
   */


  public static void printContents(Vector<String> contents) {
    for (String content : contents) {
      System.out.println(contents);
    }
  }
}
