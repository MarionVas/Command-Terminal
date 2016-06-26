package a2;

public class Echo implements CommandInterface {
  private JFileSystem jFileSystem;
  private String[] echoParameters;

  public Echo(JFileSystem jFileSystem, String[] echoParameters) {
    this.jFileSystem = jFileSystem;
    this.echoParameters = echoParameters;
  }

  public void execute() {
    if (echoParameters[1].equals(">")) {
      EchoOverwrite overwriteFile =
          new EchoOverwrite(jFileSystem, echoParameters);
      overwriteFile.execute();
    } else if (echoParameters[1].equals(">>")) {
      EchoAppend appendFile = new EchoAppend(jFileSystem, echoParameters);
      appendFile.execute();
    } else if (echoParameters.length == 1) {
      Output.printString(echoParameters[0]);
    } else {
      Output.printError();
    }
  }
  
  public String manual() {
    EchoOverwrite overwriteFile =
        new EchoOverwrite(jFileSystem, echoParameters);
    EchoAppend appendFile = new EchoAppend(jFileSystem, echoParameters);
    
    return overwriteFile.manual() + "\n" + appendFile.manual();
  }
}
