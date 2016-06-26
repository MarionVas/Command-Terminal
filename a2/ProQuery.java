package a2;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Hashtable;

public class ProQuery {
  private JFileSystem jFileSystem;
  private History commandHistory;

  public ProQuery(JFileSystem jFileSystem) {
    this.jFileSystem = jFileSystem;
    this.commandHistory = new History();
  }

  public void sortQuery(String entry) {

    Hashtable<String, String> singleCommandKeys =
        new Hashtable<String, String>();

    Hashtable<String, String> commandKeys = new Hashtable<String, String>();

    singleCommandKeys.put("ls", "a2.LS");
    singleCommandKeys.put("exit", "a2.Exit");
    singleCommandKeys.put("pwd", "a2.PWD");
    singleCommandKeys.put("popd", "a2.PopD");

    commandKeys.put("cd", "a2.CD");
    commandKeys.put("cat", "a2.Cat");
    commandKeys.put("echo", "a2.Echo");
    commandKeys.put("ls", "a2.LS");
    commandKeys.put("man", "a2.Man");
    commandKeys.put("mkdir", "a2.Mkdir");
    commandKeys.put("pushd", "a2.PushD");

    commandHistory.addInput(entry);

    while (entry.startsWith(" ")) {
      entry = entry.substring(1);
    }

    String[] splitEntry = entry.split("\\s+");
    System.out.println(Arrays.toString(splitEntry));
    System.out.println(splitEntry[0]);

    if (splitEntry.length <= 1 && !splitEntry[0].equals("history")) {
      System.out.println("over here1");
      try {
        String commandName = singleCommandKeys.get(splitEntry[0]);
        // System.out.println(commandName);
        try {
          CommandInterface commandInstance =
              (CommandInterface) Class.forName(commandName)
                  .getConstructor(JFileSystem.class).newInstance(jFileSystem);
          commandInstance.execute();

        } catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException
            | SecurityException e) {
          e.printStackTrace();
        }
      } catch (NullPointerException e) {
        Output.printError();
      }
    } else if (splitEntry.length <= 1 && splitEntry[0].equals("history")) {
      commandHistory.getHistory();

    } else if (splitEntry.length > 1 && splitEntry[0].equals("history")) {
      System.out.println("over here3 pushd");
      commandHistory
          .getHistory(Arrays.copyOfRange(splitEntry, 1, splitEntry.length));


    } else {
      try {
        String commandName = commandKeys.get(splitEntry[0]);
        String[] commandParameters =
            Arrays.copyOfRange(splitEntry, 1, splitEntry.length);
        // System.out.println(commandName);
        // System.out.println(Arrays.toString(commandParameters));
        try {
          CommandInterface commandInstance =
              (CommandInterface) Class.forName(commandName)
                  .getConstructor(JFileSystem.class, String[].class)
                  .newInstance(jFileSystem, commandParameters);
          commandInstance.execute();

        } catch (ClassNotFoundException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException
            | SecurityException | InstantiationException
            | IllegalAccessException e) {
          e.printStackTrace();
        }
      } catch (NullPointerException e) {
        Output.printError();
      }
    }

  }
  
  /**
   * The purpose of this method is return the JFileSystem of the ProQuery class
   */
  public JFileSystem getFileSystem()
  {
    return this.jFileSystem;
  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    // String teststring = " this is a test ";

    /*
     * String [] test = teststring.split(" ");
     * System.out.println(Arrays.toString(test));
     * System.out.println(test.length); System.out.println(test[0].length());
     * System.out.println("this   is a test".substring(1)); System.out.println(
     * "              this is a test".startsWith(" "));
     */


  }

}
