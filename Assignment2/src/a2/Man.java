package a2;

public class Man implements CommandInterface { //requires execute() command
  
  private JFileSystem jFileSystem;
  private String[] commandName;

  public Man(JFileSystem jFileSystem, String[] commandName) {
    this.jFileSystem = jFileSystem;
    this.commandName = commandName;
  }
}
