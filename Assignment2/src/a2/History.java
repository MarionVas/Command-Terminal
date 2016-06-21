package a2;

import java.util.Vector;

public class History implements CommandInterface //need execute method()
{
  // variable declaration
  private Vector<String> inputHistory;
  private int location;
  private Vector<String> output;

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
    this.inputHistory.add(input);
    
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
    // change the string array into a single digit
    int location = Integer.parseInt(strLocation[0]);
    
    // creating a vector string to output that displays the history
    // appropriately from a location specified
    output = (Vector<String>) inputHistory.subList(
        inputHistory.size()-location, inputHistory.size()); 
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    
  }

}
