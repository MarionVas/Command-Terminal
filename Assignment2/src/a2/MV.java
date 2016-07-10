package a2;

public class MV implements CommandInterface {
  private JFileSystem insertedFileSystem;
  private String[] classPaths;
  

  public MV(JFileSystem insertedFileSystem, String[] classPaths) {
    this.insertedFileSystem = insertedFileSystem;
    this.classPaths = classPaths;
  }
  
  public void execute() {
    // TODO Auto-generated method stub
    
  }

  public String manual() {
    // TODO Auto-generated method stub
    return null;
  }

}
