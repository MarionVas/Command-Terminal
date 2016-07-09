package a2;

import java.util.Vector;

public class Number implements CommandInterface
{
  private String commandNumber;
  private ProQuery process;
  private JFileSystem fileManager;
  private History commandHistory;
  
  // Standard constructor for the number class
  public Number(JFileSystem fileManager, ProQuery process, String number)
  {
    this.process = process;
    this.commandNumber = number;
    this.fileManager = fileManager;
  }

  @Override
  public void execute() 
  {
    int command = 0;
    // check if the thing is a number
    try
    {
      command = Integer.parseInt(commandNumber);
    } catch (Exception e)
    {
      Output.printError();
    }
    
    //commandHistory = process.getHistory();
    Vector commandHistory = process.getHistory().getList();
    ProQuery doCommand = new ProQuery(fileManager);
    String query = (String) commandHistory.get(command);
    doCommand.sortQuery(query);
    
    
    
  }

  @Override
  public String manual() {
    // TODO Auto-generated method stub
    return null;
  }
  
  

}
