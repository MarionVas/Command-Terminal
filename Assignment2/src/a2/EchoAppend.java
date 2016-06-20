package a2;

public class EchoAppend extends EchoOverwrite {

  public EchoAppend(String parameter) {
    super(parameter);
  }

  private String findFileName() {
    String fileName = getParameter().substring(getIndexOfSymbol() + 2,
        getParameter().length());
    return fileName;
  }

  private void replace(File file, String body) {
    file.addToBody(body);
  }
}
