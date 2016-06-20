package a2;

public class ProQuery {

  public static void sortQuery(String entry, boolean validity) {
    if (validity == false) {
      // either print or send something to output about invalid command
    } else {
      if (entry.length() <= 4) {
        if (entry == "exit") {
          // call exit command
        } else if (entry == "pwd") {
          // call pwd command
        } else if (entry == "ls") {
          // call ls command
        } else if (entry == "popd") {
          // call popd command
        }
      } else {
        if (entry.startsWith("cd ")) {
          // call cd command
        } else if (entry.startsWith("cat ")) {
          // call cat command
        } else if (entry.startsWith("echo ")) {
          // call appropriate echo command
        } else if (entry.startsWith("history ")) {
          // call history command
        } else if (entry.startsWith("ls ")) {
          // call ls command with argument
        } else if (entry.startsWith("mkdir ")) {
          // call mkdir command
        } else if (entry.startsWith("pushd ")) {
          // call pushd command
        }
      }
    }
  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
