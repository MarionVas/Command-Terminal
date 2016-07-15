package a2;

import java.util.Vector;

public class Number implements CommandInterface
{
  private String commandNumber;
  private ProQuery process;
  private JFileSystem fileManager;
  private History commandHistory;
  private String output = "";
  
  // Standard constructor for the number class
  public Number(JFileSystem fileManager, ProQuery process, String number)
  {
    this.process = process;
    this.commandNumber = number;
    this.fileManager = fileManager;
  }

  @Override
  public String execute() 
  {
    System.out.println("MADE IT TO CLASS");
    int commandLocation = 0;
    // check if the parameter is a number
    try
    {
      commandLocation = Integer.parseInt(commandNumber);
    } catch (Exception e)
    {
      Output.printError();
    }
    
    // Get History from ProQuery
    History commandHistory = process.getHistory();
    Vector<String> allCommands = commandHistory.getList();
    // Get the command at the specified user location
    String query = allCommands.get(commandLocation);
    // Remove the !number command from the history
    commandHistory.popHistory();
    // set the ProQuery history
    process.setHistory(commandHistory);
    // do the past command
    process.sortQuery(query);
    
    return output;  
  }

  @Override
  public String manual() {
    // TODO Auto-generated method stub
    return null;
  }
  
  

}
