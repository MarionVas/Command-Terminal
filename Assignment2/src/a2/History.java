package a2;

import java.util.ArrayList;
import java.util.List;

public class History implements CommandInterface //need execute method()
{
  
  private List<String> inputHistory;
  
  // Default constructor
  public History()
  {
    // make a new list
    this.inputHistory = new ArrayList<String>();
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
   * The purpose of this function is to return the inputHistory list
   */
  public List getHistory()
  {
    return inputHistory;
  }
  
  /**
   * The purpose of this overloaded method is to return the a sublist of
   * the inputHistory list containing the last n commands as specified by
   * the user
   * 
   * @param location, the amount of previous commands the user wishes to see
   */
  public List getHistory(int location)
  {
    return inputHistory.subList((inputHistory.size()-location),
        inputHistory.size());
  }
}
