package a2;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Hashtable;

public class ProQuery {
  // This is a JFileSystem that commands will use to execute upon
  private JFileSystem jFileSystem;
  // This is a History object which will save given entries
  private History commandHistory;
  private String[] singleCommandKeysList = new String[] {"ls", "exit", "pwd", "popd"};
  private String[] singleCommandValuesList = new String[] {"a2.LS", "a2.Exit", "a2.PWD", "a2.PopD"};
  private String[] commandKeysList = new String[] {"cd", "cat", "echo", "ls", "man", "mkdir", "pushd"};
  private String[] commandValuesList = new String[] {"a2.CD", "a2.Cat", "a2.Echo", "a2.LS", "a2.Man", "a2.Mkdir", "a2.PushD"};
  private Hashtable<String, String> singleCommandKeys = new Hashtable<String, String>();
  private Hashtable<String, String> commandKeys = new Hashtable<String, String>();

  /**
   * Constructs a ProQuery object which takes a JFileSystem for commands to act
   * upon.
   * 
   * @param jFileSystem FileSystem that the commands will act upon.
   */
  public ProQuery(JFileSystem jFileSystem) {
    this.jFileSystem = jFileSystem;
    this.commandHistory = new History();
  }
  
  private void populateHashtables() {
    for (int i=0; i<singleCommandKeysList.length; i++) {
      singleCommandKeys.put(singleCommandKeysList[i], singleCommandValuesList[i]);
    }
    for (int i=0; i<commandKeysList.length; i++) {
      commandKeys.put(commandKeysList[i], commandValuesList[i]);
    }
  }
  
  /**
   * The purpose of this method is to allow user to pass in a string entry which
   * the ProQuery will then appropriately differentiate and call upon the
   * appropriate command class to execute upon the ProQuery's JFileSystem.
   * 
   * @param entry A command (valid or invalid) issued by the user.
   */
  public void sortQuery(String entry) {
   /*
    // Initialize a Hashtable for command classes whose constructors require
    // no parameters (the entry will only be a single key word)
    singleCommandKeys = new Hashtable<String, String>();
    // Initialize a Hashtable for command classes whose constructors will
    // require parameters
    commandKeys = new Hashtable<String, String>();

    // Populate the singleCommandKeys hashtable with keys/values
    singleCommandKeys.put("ls", "a2.LS");
    singleCommandKeys.put("exit", "a2.Exit");
    singleCommandKeys.put("pwd", "a2.PWD");
    singleCommandKeys.put("popd", "a2.PopD");

    // Populate the CommandKeys hashtable with keys/values
    commandKeys.put("cd", "a2.CD");
    commandKeys.put("cat", "a2.Cat");
    commandKeys.put("echo", "a2.Echo");
    commandKeys.put("ls", "a2.LS");
    commandKeys.put("man", "a2.Man");
    commandKeys.put("mkdir", "a2.Mkdir");
    commandKeys.put("pushd", "a2.PushD");
    */
    this.populateHashtables();
    
    // Save string entries inserted to the History object
    commandHistory.addInput(entry);

    // Remove any whitespace found within the beginning of the string
    while (entry.startsWith(" ")) {
      entry = entry.substring(1);
    }

    // Split the string entry into a string array with whitespace acting as
    // the separator
    String[] splitEntry = entry.split("\\s+");

    // If the given string entry is only one key word and not history
    if (splitEntry.length <= 1 && !splitEntry[0].equals("history")) {
      try {
        // Acquire the appropriate value within the hashtable
        String commandName = singleCommandKeys.get(splitEntry[0]);

        try {
          // Create an appropriate instance of the class according to the
          // string given. These constructors only require a JFileSystem
          CommandInterface commandInstance =
              (CommandInterface) Class.forName(commandName)
                  .getConstructor(JFileSystem.class).newInstance(jFileSystem);
          // Run the execute method of the instance created
          commandInstance.execute();

          // Catch any unwanted cases/exceptions
        } catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException
            | SecurityException e) {
          e.printStackTrace();
        }
        // If the key/value is not found within the singleCommandKey hashtable,
        // produce an error message printed to the shell from the output class
      } catch (NullPointerException e) {
        Output.printError();
      }
      // If the one key word string is "history", execute the history class
    } else if (splitEntry.length <= 1 && splitEntry[0].equals("history")) {
      commandHistory.execute();

      // If the given string is history with additional parameters
    } else if (splitEntry.length > 1 && splitEntry[0].equals("history")) {
      // Execute the history class with the additional parameters
      commandHistory
          .execute(Arrays.copyOfRange(splitEntry, 1, splitEntry.length));


    } else {
      try {
        // Split the given string into its command key word and its parameters
        String commandName = commandKeys.get(splitEntry[0]);
        String[] commandParameters =
            Arrays.copyOfRange(splitEntry, 1, splitEntry.length);

        try {
          // Create an appropriate instance of the class according to the
          // string given. These constructors require a JFileSystem and
          // a string array
          CommandInterface commandInstance =
              (CommandInterface) Class.forName(commandName)
                  .getConstructor(JFileSystem.class, String[].class)
                  .newInstance(jFileSystem, commandParameters);
          // Run the execute method of the instance created
          commandInstance.execute();

          // Catch any unwanted cases/exceptions
        } catch (ClassNotFoundException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException
            | SecurityException | InstantiationException
            | IllegalAccessException e) {
          e.printStackTrace();
        }
        // If the key/value is not found within the CommandKey hashtable,
        // produce an error message printed to the shell from the output class
      } catch (NullPointerException e) {
        Output.printError();
      }
    }

  }

  /**
   * The purpose of this method is return the JFileSystem of the ProQuery class
   */
  public JFileSystem getFileSystem() {
    return this.jFileSystem;
  }
  
  public History getHistory()
  {
    return this.commandHistory;
  }

}
