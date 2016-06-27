package a2;

import java.util.Arrays;

public class Echo implements CommandInterface {
  private JFileSystem jFileSystem;
  private String[] echoParameters;
  private String stringToOutput;
  private String echoType = null;
  private String string;

  public Echo(JFileSystem jFileSystem, String[] echoParameters) {
    this.jFileSystem = jFileSystem;
    string = echoParameters[0].substring(1, echoParameters[0].length() - 1);
    if (java.util.Arrays.asList(echoParameters).contains(">")) {
      this.echoParameters = fixEchoParameters(echoParameters, ">");
    } else if (java.util.Arrays.asList(echoParameters).contains(">>")) {
      this.echoParameters = fixEchoParameters(echoParameters, ">>");
    } else {
      this.echoParameters = echoParameters;
    }
  }

  public void execute() {
    if (echoParameters.length == 1) {
      stringToOutput = string;
      Output.printString(stringToOutput);
    } else if (echoParameters[1].equals(">")) {
      setEchoType("overwrite");
      EchoOverwrite overwriteFile =
          new EchoOverwrite(jFileSystem, echoParameters);
      overwriteFile.execute();

    } else if (echoParameters[1].equals(">>")) {
      setEchoType("append");
      EchoAppend appendFile = new EchoAppend(jFileSystem, echoParameters);
      appendFile.execute();
    } else {
      Output.printError();
    }
  }

  private String[] fixEchoParameters(String[] echoParameters, String echoSign) {
    String[] fixedEchoParameters = new String[3];
    int echoSignIndex =
        java.util.Arrays.asList(echoParameters).indexOf(echoSign);

    String[] preSign = Arrays.copyOfRange(echoParameters, 0, echoSignIndex);
    String[] postSign = Arrays.copyOfRange(echoParameters, echoSignIndex + 1,
        echoParameters.length);

    fixedEchoParameters[0] = joinStrWithSpace(preSign);
    fixedEchoParameters[1] = echoSign;
    fixedEchoParameters[2] = joinStrWithSpace(postSign);

    return fixedEchoParameters;
  }

  private String joinStrWithSpace(String[] strPara) {
    StringBuilder newString = new StringBuilder();
    for (int i = 0; i < strPara.length; i++) {
      if (i > 0) {
        newString.append(" ");
      }
      newString.append(strPara[i]);
    }
    return newString.toString();
  }

  private void setEchoType(String echoType) {
    this.echoType = echoType;
  }

  public String getEchoType() {
    return this.echoType;
  }

  public String manual() {
    EchoOverwrite overwriteFile =
        new EchoOverwrite(jFileSystem, echoParameters);
    EchoAppend appendFile = new EchoAppend(jFileSystem, echoParameters);

    return overwriteFile.manual() + "\n" + appendFile.manual();
  }

  /**
   * This method return the body of the file as a String to be printed on the
   * console
   * 
   * @return stringToOutput - the body of the file that needs to be printed
   */
  public String getStringToOutput() {
    return stringToOutput;
  }
}
