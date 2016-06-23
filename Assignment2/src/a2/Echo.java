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
      EchoOverwrite overwriteFile = new EchoOverwrite(jFileSystem, echoParameters);
      overwriteFile.execute();
    } else if (echoParameters[1].equals(">>")) {
      EchoAppend appendFile = new EchoAppend(jFileSystem, echoParameters);
      appendFile.execute();
    } else {
      Output.printError();
    }
  }
}
