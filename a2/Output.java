package a2;

public class Output {
  public static void printError() {
    System.out.println("That was not a recognized command.");
  }

  public void printPathError() {
    System.out.println("That was not a valid path.");
  }

  public void printFileNameError() {
    System.out.println("That was not a valid file name.");
  }

  public void printContents(String contents) {
    System.out.println(contents);
  }
}
