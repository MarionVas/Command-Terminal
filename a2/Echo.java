package a2;

import java.util.Arrays;

public class Echo implements CommandInterface {
  private JFileSystem jFileSystem;
  private String[] echoParameters;
  private String stringToOutput;

  public Echo(JFileSystem jFileSystem, String[] echoParameters) {
    this.jFileSystem = jFileSystem;

    if (java.util.Arrays.asList(echoParameters).contains(">")) {
      this.echoParameters = Echo.fixEchoParameters(echoParameters, ">");
    } else if (java.util.Arrays.asList(echoParameters).contains(">>")) {
      this.echoParameters = Echo.fixEchoParameters(echoParameters, ">>");
    } else {
      this.echoParameters = echoParameters;
    }
    System.out.println(Arrays.toString(this.echoParameters));
  }

  public void execute() {
    if (echoParameters.length == 1) {
      stringToOutput = echoParameters[0];
      Output.printString(stringToOutput);
    } else if (echoParameters[1].equals(">")) {
      EchoOverwrite overwriteFile =
          new EchoOverwrite(jFileSystem, echoParameters);
      overwriteFile.execute();

    } else if (echoParameters[1].equals(">>")) {
      EchoAppend appendFile = new EchoAppend(jFileSystem, echoParameters);
      appendFile.execute();
    } else {
      Output.printError();
    }
  }

  private static String[] fixEchoParameters(String[] echoParameters,
      String echoSign) {
    String[] fixedEchoParameters = new String[3];
    int echoSignIndex =
        java.util.Arrays.asList(echoParameters).indexOf(echoSign);

    String[] preSign = Arrays.copyOfRange(echoParameters, 0, echoSignIndex);
    String[] postSign = Arrays.copyOfRange(echoParameters, echoSignIndex + 1,
        echoParameters.length);

    fixedEchoParameters[0] = Echo.joinStrWithSpace(preSign);
    fixedEchoParameters[1] = echoSign;
    fixedEchoParameters[2] = Echo.joinStrWithSpace(postSign);

    return fixedEchoParameters;
  }

  private static String joinStrWithSpace(String[] strPara) {
    StringBuilder newString = new StringBuilder();
    for (int i = 0; i < strPara.length; i++) {
      if (i > 0) {
        newString.append(" ");
      }
      newString.append(strPara[i]);
    }
    return newString.toString();
  }

  public String manual() {
    EchoOverwrite overwriteFile =
        new EchoOverwrite(jFileSystem, echoParameters);
    EchoAppend appendFile = new EchoAppend(jFileSystem, echoParameters);

    return overwriteFile.manual() + "\n" + appendFile.manual();
  }

  public String getStringToOutput() {
    return stringToOutput;
  }
}
