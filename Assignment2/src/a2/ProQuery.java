package a2;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Hashtable;

public class ProQuery {
  // This is a JFileSystem that commands will use to execute upon
  private JFileSystem jFileSystem;
  // This is a History object which will save given entries
  private History commandHistory;
  // Hashtable to contain keys/values regarding commands without parameters
  private Hashtable<String, String> singleCommandKeys =
      new Hashtable<String, String>();
  // Hashtable for commands with parameters
  private Hashtable<String, String> commandKeys =
      new Hashtable<String, String>();
  // String to be printed on console
  private String stringToOutput = "";

  /**
   * Constructs a ProQuery object which takes a JFileSystem for commands to act
   * upon.
   * 
   * @param jFileSystem - FileSystem that the commands will act upon.
   */
  public ProQuery(JFileSystem jFileSystem) {
    this.jFileSystem = jFileSystem;
    this.commandHistory = new History();
    this.populateHashtables();
  }

  /**
   * Populates ProQuery's hashtable to be used within the sortQuery process to
   * create instances of specified command classes.
   * 
   */
  private void populateHashtables() {
    // Arrays of keys/values for commands without parameters
    String[] singleCommandKeysList = new String[] {"ls", "exit", "pwd", "popd"};
    String[] singleCommandValuesList =
        new String[] {"a2.LS", "a2.Exit", "a2.PWD", "a2.PopD"};
    // Arrays of keys/values for commands with parameters
    String[] commandKeysList = new String[] {"cd", "cat", "echo", "ls", "man",
        "mkdir", "pushd", "mv", "grep", "cp", "curl"};
    String[] commandValuesList =
        new String[] {"a2.CD", "a2.Cat", "a2.Echo", "a2.LS", "a2.Man",
            "a2.Mkdir", "a2.PushD", "a2.MV", "a2.Grep", "a2.Copy", "a2.Curl"};

    // Use the instantiated arrays with keys/values to populate the hashtables
    // Populating hashtable for commands without parameters
    for (int i = 0; i < singleCommandKeysList.length; i++) {
      singleCommandKeys.put(singleCommandKeysList[i],
          singleCommandValuesList[i]);
    }
    // Populating hashtable for commands with parameters
    for (int i = 0; i < commandKeysList.length; i++) {
      commandKeys.put(commandKeysList[i], commandValuesList[i]);
    }
  }

  /**
   * The purpose of this method is to allow user to pass in a string entry which
   * the ProQuery will then appropriately differentiate and call upon the
   * appropriate command class to execute upon the ProQuery's JFileSystem.
   * 
   * @param entry - A command (valid or invalid) issued by the user.
   */
  public void sortQuery(String entry) {
    // Initialize a string array to contain arguments for a given command
    // These arguments may or may not exist
    String[] commandParameters = {};


    // Save string entries inserted to the History object
    commandHistory.addInput(entry);

    // Remove any whitespace found within the beginning of the string
    while (entry.startsWith(" ")) {
      entry = entry.substring(1);
    }

    // Split the string entry into a string array with whitespace acting as
    // the separator
    String[] splitEntry = entry.split("\\s+");
    if (splitEntry.length > 1) {
      String[] fixCommandParameters =
          Arrays.copyOfRange(splitEntry, 1, splitEntry.length);
      commandParameters = Arrays.copyOfRange(fixCommandParameters, 0,
          findSignIndexOrLast(fixCommandParameters));
    }

    try {
      // If the given string entry is only one key word and not history
      if (commandParameters.length == 0 && !splitEntry[0].equals("history")
          && !splitEntry[0].startsWith("!")) {
        // Acquire the appropriate value within the hashtable
        String commandName = singleCommandKeys.get(splitEntry[0]);

        // Create an appropriate instance of the class according to the
        // string given. These constructors only require a JFileSystem
        CommandInterface commandInstance =
            (CommandInterface) Class.forName(commandName)
                .getConstructor(JFileSystem.class).newInstance(jFileSystem);
        // Run the execute method of the instance created
        stringToOutput = commandInstance.execute();

        // If the one key word string is "history", execute the history class
        // through the queryHistory method
      } else if (splitEntry[0].equals("history")) {
        queryHistory(commandParameters);

        // If the input is calling the "!" command, execute the Number class
        // through the queryNumber method
      } else if (commandParameters.length == 0
          && splitEntry[0].startsWith("!")) {
        queryNumber(splitEntry);

      } else {
        // Split the given string into its command key word and its parameters
        String commandName = commandKeys.get(splitEntry[0]);
        System.out.println(Arrays.toString(commandParameters));
        // Create an appropriate instance of the class according to the
        // string given. These constructors require a JFileSystem and
        // a string array
        CommandInterface commandInstance =
            (CommandInterface) Class.forName(commandName)
                .getConstructor(JFileSystem.class, String[].class)
                .newInstance(jFileSystem, commandParameters);
        // Run the execute method of the instance created
        stringToOutput = commandInstance.execute();
      }

      // If the input entry contained redirection arguments, redirect the output
      // to a file appropriately. Otherwise, print output on console as usual
      if (checkForRedir(splitEntry) && !stringToOutput.equals("")) {
        commandStringToFile(splitEntry, stringToOutput);
      } else {
        commandStringToOutput(stringToOutput);
      }

    } catch (NullPointerException e) {
      Output.printError();
    } catch (ClassNotFoundException e) {
      Output.printError();
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private String queryHistory(String[] commandParameters) {
    String strOut = "";
    if (commandParameters.length == 0) {
      strOut = commandHistory.execute();
    } else if (commandParameters.length >= 1) {
      strOut = commandHistory.execute(commandParameters);
    }
    return strOut;
  }


  /**
   * Constructs a Number object which takes the ProQuery and its jFileSystem to
   * act upon. This method then calls upon and returns the result of Number's
   * execute method.
   * 
   * @param splitEntry - The user's input to be parsed for key characters
   */
  private String queryNumber(String[] splitEntry) {
    // Acquire the number character that appears after "!"
    String num = splitEntry[0].substring(splitEntry[0].indexOf("!") + 1,
        splitEntry[0].length());
    // Create a Number instance and execute
    Number processNumber = new Number(jFileSystem, this, num);
    return processNumber.execute();
  }

  /**
   * Returns the index of the last occurrence of the outfile redirection
   * characters ">" or ">>" in a given String array. If the characters are not
   * found, return the index of the last element.
   * 
   * @param insertStringArray - A formatted user input string array to be parsed
   *        for redirection characters
   */
  private int findSignIndexOrLast(String[] insertStringArray) {
    // Initialize index at last element
    int index = insertStringArray.length;
    // If the String array contains ">", get the value of its index
    if (Arrays.asList(insertStringArray).contains(">")) {
      index = Arrays.asList(insertStringArray).lastIndexOf(">");
      // If the String array contains ">>", get the value of its index
    } else if (Arrays.asList(insertStringArray).contains(">>")) {
      index = Arrays.asList(insertStringArray).lastIndexOf(">>");
    }
    return index;
  }

  /**
   * Takes an already split entry and looks for redirection parameters to be
   * appropriately formatted for redirection using a helper function.
   * 
   * @param redirParameters - A split user entry that is parsed for redir chars
   */
  private String[] fixForOutfileRedirection(String[] redirParameters) {
    // Format appropriately if the String array contains ">" or ">>"
    if (Arrays.asList(redirParameters).contains(">")) {
      System.out.println("HERE1");
      return fixRedirectionParameters(redirParameters, ">");
    } else if (Arrays.asList(redirParameters).contains(">>")) {
      return fixRedirectionParameters(redirParameters, ">>");
    } else {
      // Return the String array unchanged if it contains no redir chars
      return redirParameters;
    }
  }

  /**
   * Takes an already split entry containing redirection characters and
   * appropriately separates it into three or two elements depending upon where
   * the ">>" or ">" substrings are located.
   * 
   * @param parameters - A split user entry that is to be formatted
   *        appropriately for redirection of command outputs
   * @param sign - The redirection character found within parameters
   */
  private String[] fixRedirectionParameters(String[] parameters, String sign) {
    int signIndex = Arrays.asList(parameters).indexOf(sign);
    // Elements before and after the redir char
    String[] preSign = Arrays.copyOfRange(parameters, 0, signIndex);
    String[] postSign =
        Arrays.copyOfRange(parameters, signIndex + 1, parameters.length);
    // If the first character of the test is a redir char
    if (signIndex == 0) {
      String[] fixedEchoParameters = new String[2];
      fixedEchoParameters[0] = sign;
      fixedEchoParameters[1] = joinStrWithSpace(postSign);
      return fixedEchoParameters;
      // If the redir is called on a command with command arguments
    } else {
      String[] fixedEchoParameters = new String[3];
      fixedEchoParameters[0] = joinStrWithSpace(preSign);
      fixedEchoParameters[1] = sign;
      fixedEchoParameters[2] = joinStrWithSpace(postSign);
      return fixedEchoParameters;
    }


  }

  /**
   * Takes a String array and concatenates its elements to produce a single
   * string with a space between each element.
   * 
   * @param strPara - String elements to be concatenated
   */
  private String joinStrWithSpace(String[] strPara) {
    // Initiate a StringBuilder
    StringBuilder newString = new StringBuilder();
    for (int i = 0; i < strPara.length; i++) {
      // Don't add a space character at the very beginning of the string
      if (i > 0) {
        newString.append(" ");
      }
      newString.append(strPara[i]);
    }
    return newString.toString();
  }

  /**
   * Takes user input parameters and the output from an execute method and
   * redirects the output to a file instead of the console accordingly
   * 
   * @param inputArguments - String elements to be concatenated
   */
  private void commandStringToFile(String[] inputArguments,
      String commandOutput) {
    // Name of output file from inputArguments
    String[] outfileSection = getFileSection(inputArguments);
    if (outfileSection.length == 1) {
      String outfile = outfileSection[0];
      // Don't produce the outfile if there nothing to print from a command's
      // execute method
      if (!commandOutput.equals("")
          && Arrays.asList(inputArguments).contains(">")) {
        OutputToFile.overwrite(jFileSystem, commandOutput, outfile);
      } else if (!commandOutput.equals("")
          && Arrays.asList(inputArguments).contains(">>")) {
        OutputToFile.append(jFileSystem, commandOutput, outfile);
      }
    } else {
      System.err.println("That is not a valid OUTFILE");
    }
  }

  private String[] getFileSection(String[] inputArguments) {
    String[] fileSection = {};
    int index = findSignIndexOrLast(inputArguments);
    if (Arrays.asList(inputArguments).contains(">")) {
      fileSection =
          Arrays.copyOfRange(inputArguments, index + 1, inputArguments.length);
    } else if (Arrays.asList(inputArguments).contains(">>")) {
      fileSection =
          Arrays.copyOfRange(inputArguments, index + 1, inputArguments.length);
    }
    return fileSection;
  }

  /**
   * Takes the string output from an execute method and prints it to the
   * console.
   * 
   * @param commandOutput - String to be printed on console
   */
  private void commandStringToOutput(String commandOutput) {
    // Print on console if the string is not empty
    if (!commandOutput.equals("")) {
      System.out.print(commandOutput + "\n");
    }
  }

  /**
   * Checks to see if a redir char exists within the given String array. Returns
   * true if a redir parameter exists.
   * 
   * @param input - String elements to be parsed for redir chars
   */
  private boolean checkForRedir(String[] input) {
    boolean checkRedir = false;
    // Return true if either redir char exists within the String array
    if (Arrays.asList(input).contains(">")
        | Arrays.asList(input).contains(">>")) {
      checkRedir = true;
    }
    return checkRedir;
  }

  /**
   * The purpose of this method is return the JFileSystem of the ProQuery object
   * 
   * @return jFileSystem
   */
  public JFileSystem getFileSystem() {
    return this.jFileSystem;
  }

  /**
   * A method to set the history of the ProQuery object
   * 
   * @param history, the history object to replace the current one
   */
  public void setHistory(History history) {
    this.commandHistory = history;
  }

  /**
   * The purpose of this method is to return the History of the ProQuery object
   * 
   * @return commandHistory
   */
  public History getHistory() {
    return this.commandHistory;
  }


}
