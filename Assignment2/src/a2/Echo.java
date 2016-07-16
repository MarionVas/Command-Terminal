package a2;

import java.util.Arrays;

public class Echo implements CommandInterface {
  private JFileSystem jFileSystem;
  private String[] echoParameters;
  private String stringToOutput = "";

  public Echo(JFileSystem jFileSystem, String[] echoParameters) {
    this.jFileSystem = jFileSystem;
    this.echoParameters = echoParameters;
  }

  public String execute() {
    if (echoParameters.length == 1) {
      if (echoParameters[0].startsWith("\"")
          && echoParameters[0].endsWith("\"")) {
        stringToOutput =
            echoParameters[0].substring(1, echoParameters[0].length() - 1);
      } else {
        System.out.println("That was not a valid String");
      }
    } else {
      if (echoParameters[0].startsWith("\"")
          && echoParameters[echoParameters.length - 1].endsWith("\"")) {
        for (String eachString : echoParameters) {
          stringToOutput += eachString + " ";
        }
        stringToOutput =
            stringToOutput.substring(0, stringToOutput.length() - 1);
      } else {
        System.out.println("That was not a valid String");
      }
    }
    return stringToOutput;
  }


  public String manual() {
    EchoOverwrite overwriteFile =
        new EchoOverwrite(jFileSystem, echoParameters);
    EchoAppend appendFile = new EchoAppend(jFileSystem, echoParameters);

    return overwriteFile.manual() + "\n" + appendFile.manual();
  }

}
