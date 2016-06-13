package a2;

public class Validator {

  private static boolean validEntry = false;
  private static String[] beginCommandArray =
      {"cd ", "cat ", "echo ", "history ", "ls ", "man ", "mkdir ", "pushd "};

  public static boolean validateEntry(String entry) {
    if (entry.length() <= 4) {
      if (entry == "exit" || entry == "pwd" || entry == "ls"
          || entry == "popd") {
        validEntry = true;
      }
    } else {
      for (String beginOfEntry : beginCommandArray) {
        if (entry.startsWith(beginOfEntry)) {
          validEntry = true;
        }
      }
    }
    return validEntry;
  }

  public static void main(String[] args) {
    System.out.println(Validator.validateEntry("34838"));
  }
}
