package a2;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Hashtable;

public class ProQuery {
  // This is a JFileSystem that commands will use to execute upon
  private JFileSystem jFileSystem;
  // This is a History object which will save given entries
  private History commandHistory;
  private Hashtable<String, String> singleCommandKeys =
      new Hashtable<String, String>();
  private Hashtable<String, String> commandKeys =
      new Hashtable<String, String>();
  private String stringToOutput = "";

  /**
   * Constructs a ProQuery object which takes a JFileSystem for commands to act
   * upon.
   * 
   * @param jFileSystem FileSystem that the commands will act upon.
   */
  public ProQuery(JFileSystem jFileSystem) {
    this.jFileSystem = jFileSystem;
    this.commandHistory = new History();
    this.populateHashtables();
  }

  private void populateHashtables() {
    String[] singleCommandKeysList = new String[] {"ls", "exit", "pwd", "popd"};
    String[] singleCommandValuesList =
        new String[] {"a2.LS", "a2.Exit", "a2.PWD", "a2.PopD"};
    String[] commandKeysList = new String[] {"cd", "cat", "echo", "ls", "man",
        "mkdir", "pushd", "mv", "grep"};
    String[] commandValuesList = new String[] {"a2.CD", "a2.Cat", "a2.Echo",
        "a2.LS", "a2.Man", "a2.Mkdir", "a2.PushD", "a2.MV", "a2.Grep"};
    for (int i = 0; i < singleCommandKeysList.length; i++) {
      singleCommandKeys.put(singleCommandKeysList[i],
          singleCommandValuesList[i]);
    }
    for (int i = 0; i < commandKeysList.length; i++) {
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
    // Initialize
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
      String[] fixedCommandParameters = fixForOutfileRedirection(
          Arrays.copyOfRange(splitEntry, 1, splitEntry.length));
      commandParameters = Arrays.copyOfRange(fixedCommandParameters, 0,
          findSignIndexOrLast(fixedCommandParameters));
    }

    try {
      // If the given string entry is only one key word and not history
      if (commandParameters.length == 0 && !splitEntry[0].equals("history")) {
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
      } else if (splitEntry[0].equals("history")) {
        //queryHistory(commandParameters);

      } else if (commandParameters.length == 0 && splitEntry[0].startsWith("!")) {
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
      
      
      if (checkForRedir(splitEntry) && !stringToOutput.equals("")) {
        stringToFile(splitEntry, stringToOutput);
      } else {
        stringToOutput(stringToOutput);
      }

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
/*
  private String queryHistory(String[] commandParameters) {
    String strOut;
    if (commandParameters.length == 0) {
      strOut = commandHistory.execute();
    } else if (commandParameters.length >= 1) {
      strOut = commandHistory.execute(commandParameters);
    }
    return strOut;
  }*/

  
  private String queryNumber(String[] splitEntry) {
    String num = splitEntry[0].substring(splitEntry[0].indexOf("!") + 1, splitEntry[0].length());
    Number processNumber = new Number(jFileSystem, this, num);
    return processNumber.execute();
  }

  
  private int findSignIndexOrLast(String[] insertStringArray) {
    int index = insertStringArray.length;
    if (Arrays.asList(insertStringArray).contains(">")) {
      index = Arrays.asList(insertStringArray).indexOf(">");
    } else if (Arrays.asList(insertStringArray).contains(">>")) {
      index = Arrays.asList(insertStringArray).indexOf(">>");
    }
    return index;
  }

  private String[] fixForOutfileRedirection(String[] redirParameters) {
    if (Arrays.asList(redirParameters).contains(">")) {
      System.out.println("HERE1");
      return fixRedirectionParameters(redirParameters, ">");
    } else if (Arrays.asList(redirParameters).contains(">>")) {
      return fixRedirectionParameters(redirParameters, ">>");
    } else {
      return redirParameters;
    }
  }

  private String[] fixRedirectionParameters(String[] parameters, String sign) {
    int signIndex = Arrays.asList(parameters).indexOf(sign);

    String[] preSign = Arrays.copyOfRange(parameters, 0, signIndex);
    String[] postSign =
        Arrays.copyOfRange(parameters, signIndex + 1, parameters.length);

    if (signIndex == 0) {
      String[] fixedEchoParameters = new String[2];
      System.out.println("HERE2");
      fixedEchoParameters[0] = sign;
      fixedEchoParameters[1] = joinStrWithSpace(postSign);
      return fixedEchoParameters;
    } else {
      String[] fixedEchoParameters = new String[3];
      System.out.println("HERE3");
      fixedEchoParameters[0] = joinStrWithSpace(preSign);
      fixedEchoParameters[1] = sign;
      fixedEchoParameters[2] = joinStrWithSpace(postSign);
      return fixedEchoParameters;
    }


  }

  private String joinStrWithSpace(String[] strPara) {
    StringBuilder newString = new StringBuilder();
    for (int i = 0; i < strPara.length; i++) {
      if (i > 0) {
        newString.append(" ");
      }
      newString.append(strPara[i]);
    }
    return newString.toString();
  }

  private void stringToFile(String[] inputArguments, String commandOutput) {
    String outfile = inputArguments[inputArguments.length - 1];
    System.out.println(outfile);
    if (!commandOutput.equals("")
        && Arrays.asList(inputArguments).contains(">")) {
      OutputToFile.overwrite(jFileSystem, commandOutput, outfile);
    } else if (!commandOutput.equals("")
        && Arrays.asList(inputArguments).contains(">>")) {
      OutputToFile.append(jFileSystem, commandOutput, outfile);
    }
  }

  private void stringToOutput(String commandOutput) {
    if (!commandOutput.equals("")) {
      System.out.print(commandOutput + "\n");
    }
  }

  private boolean checkForRedir(String[] input) {
    boolean checkRedir = false;
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
