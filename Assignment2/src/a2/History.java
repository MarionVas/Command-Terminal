package a2;

import java.util.List;
import java.util.Vector;

public class History implements CommandInterface // need execute method()
{
  // variable declaration
  private Vector<String> inputHistory;
  private Vector<String> output;
  private int commandNumber = 0;
  private int commandLocation;
  private int location;
  private List<String> subHistory;

  // Default constructor
  public History() {
    // make a new list
    this.inputHistory = new Vector<String>();
  }

  /**
   * The purpose of this method is to add an element to the list
   * 
   * @param input, the string value of the item to be added to the list
   */
  public void addInput(String input) {
    // iterate the command number
    commandNumber++;
    // add the command input with the appropriate formatting
    this.inputHistory.add(commandNumber + ". " + input);

  }

  /**
   * The purpose of this method is to output the command history through the
   * output class
   */
  public void execute() {
    output = inputHistory;
    Output.printContents(output);
  }

  /**
   * The purpose of this method is to output the previous strLocation commands
   * as given by the user and output using the output class
   * 
   * @param strLocation, will contain one value which will be the amount of
   *        previous commands the user wishes to see
   */
  public void execute(String[] strLocation) {
    // Determine if the given string can be turned into an integer
    // If not, display an error message
    try {
      // convert the given string array into an integer
      location = Integer.parseInt(strLocation[0]);

      // makes sure the user cannot enter a negative number
      if (location >= 0) {
        // if the user chose a location larger than the size of the history
        // vector, choose zero instead - as per command terminal functionality
        commandLocation = Math.max(inputHistory.size() - location, 0);

        // storing history from given command number to the end of the
        // history
        subHistory = inputHistory.subList(commandLocation, inputHistory.size());

        // creating a vector string to output that displays the history
        // appropriately from a location specified or 0 if need be
        Output.printContents(subHistory);
      } else {
        Output.printError();
      }
    } catch (Exception e) {
      Output.printNumberError();
    }
  }

  /**
   * A method to get the full inputHistory solely for testing
   * 
   * @return a vector<String> containing the history
   */
  public Vector<String> getList() {
    return this.inputHistory;
  }

  /**
   * A method to get the input history sublist solely for testing WILL NOT WORK
   * unless the history variable is instantiated
   * 
   * @return a List<String> containing the sublist history
   */
  public List<String> getSubList() {
    return this.subHistory;
  }



  public String manual() {
    return "history [number] - Prints out past/recently input commands during\n"
        + "the session. The given value specifies the last number of commands\n"
        + "to output.\n";

  }

}
