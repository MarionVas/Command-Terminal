package a2;

import java.util.ArrayList;
import java.util.List;
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
    this.location = 0;

  }
  
  public History(int location)
  {
    // make a new list
    this.inputHistory = new Vector<String>();
    this.location = location;

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
   * The purpose of this method is to find the location of the 
   */
  public void execute()
  {
    // Error fixing, if the user picks a location larger than the command
    // size it will default to 0
    if (this.location == 0)
    {
      output = inputHistory;
    }
    else
    {
      // Error checking to prevent the user from picking a history location
      // that is bigger than the history vector size
      int commandLocation = Math.max(inputHistory.size()-this.location,0);
      
      // creating a vector string to output that displays the history
      // appropriately from a location specified
      output = (Vector<String>) inputHistory.subList(
          inputHistory.size()-commandLocation, inputHistory.size());
    }

    Output.printContents(output);
  }
}
