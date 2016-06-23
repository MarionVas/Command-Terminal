package a2;

import java.util.List;
import java.util.Vector;

public class History implements CommandInterface //need execute method()
{
  // variable declaration
  private Vector<String> inputHistory;
  private int location;
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
    // Determine if the given string can be turned into an integer
    // If not, display an error message
    try
    {
      // convert the given string array into an integer
      int location = Integer.parseInt(strLocation[0]);
      
      // if the user chose a location larger than the size of the history
      // vector, choose zero instead - as per command terminal functionality
      commandLocation = Math.max(inputHistory.size()-location, 0);
      
      // creating a vector string to output that displays the history
      // appropriately from a location specified or 0 if need be
      
      for (int i = commandLocation; i < inputHistory.size(); i++)
      {
        output.add(inputHistory.get(i));
      }
      
      Output.printContents(output);
      
    } catch (Exception e){
      Output.printNumberError();
      

      
    }


    
 
  }
  
  public static void main(String[] args) {
    History h = new History();
    h.addInput("hi");
    h.addInput("my");
    h.addInput("name");
    h.addInput("Adnan");
    h.getHistory();
    String[] taco = {"2"};
    h.getHistory(taco);
  }
  @Override
  public void execute() {
    // TODO Auto-generated method stub
    
  }

}
