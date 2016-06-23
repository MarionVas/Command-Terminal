package a2;

import java.util.List;
import java.util.Vector;

public class History implements CommandInterface //need execute method()
{
  // variable declaration
  private Vector<String> inputHistory;
  private Vector<String> output;
  private int commandNumber = 0;
  private int commandLocation;

  // Default constructor
  public History()
  {
    // make a new list
    this.inputHistory = new Vector<String>();
  }
  
  /**
   * The purpose of this method is to add an element to the list
   * 
   * @param input, the string value of the item to be added to the list
   */
  public void addInput(String input)
  {
    // iterate the command number
    commandNumber++;
    // add the command input with the appropriate formatting
    this.inputHistory.add(commandNumber + ". " + input);
    
  }
    
  /**
   * The purpose of this method is to output the command history
   * through the output class
   */
  public void getHistory()
  {
    output = inputHistory;
    Output.printContents(output);
  }
  
  /**
   * The purpose of this method is to output the previous strLocation commands
   * as given by the user and output using the output class
   * 
   * @param strLocation, will contain one value which will be the amount
   *        of previous commands the user wishes to see
   */
  public void getHistory(String[] strLocation)
  {
    // create a location variable to store the location given by the user
    int location;
    // Determine if the given string can be turned into an integer
    // If not, display an error message
    try
    {
      // convert the given string array into an integer
      location = Integer.parseInt(strLocation[0]);
      
      // if the user chose a location larger than the size of the history
      // vector, choose zero instead - as per command terminal functionality
      commandLocation = Math.max(inputHistory.size()-location, 0);
      
      // creating a vector string to output that displays the history
      // appropriately from a location specified or 0 if need be
      
      Output.printContents(inputHistory.subList
          (inputHistory.size()-commandLocation, inputHistory.size()));

      
    } catch (Exception e){
      Output.printNumberError();    
    }
  }
  

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    
  }

}
