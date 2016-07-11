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
    // check if the parameter is a number
    try
    {
      command = Integer.parseInt(commandNumber);
    } catch (Exception e)
    {
      Output.printError();
    }
    
    //commandHistory = process.getHistory();
    // Get the vector from the history of the ProQuery
    History commandHistory = process.getHistory();
    Vector<String> allCommands = commandHistory.getList();
    // Get the command at the specified user location
    String query = allCommands.get(command);
    // Remove the !number command from the history
    allCommands.removeElementAt(allCommands.size()-1);
    // set the new history vector
    commandHistory.setHistory(allCommands);
    // set the ProQuery history
    process.setHistory(commandHistory);
    // do the past command
    process.sortQuery(query);
    
    
    
  }

  @Override
  public String manual() {
    // TODO Auto-generated method stub
    return null;
  }
  
  

}
