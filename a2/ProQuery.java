package a2;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Hashtable;

public class ProQuery {

  public static void sortQuery(String entry) {

    Hashtable<String, String> singleCommandKeys =
        new Hashtable<String, String>();

    Hashtable<String, String> commandKeys = new Hashtable<String, String>();

    singleCommandKeys.put("ls", "a2.LS");
    singleCommandKeys.put("exit", "a2.Exit");
    singleCommandKeys.put("pwd", "a2.PWD");
    singleCommandKeys.put("history", "a2.History");
    singleCommandKeys.put("popd", "a2.PushD");

    commandKeys.put("cd", "CD");
    commandKeys.put("cat", "Cat");
    commandKeys.put("echo", "");
    commandKeys.put("ls", "LS");
    commandKeys.put("man", "Man");
    commandKeys.put("mkdir", "Mkdir");
    commandKeys.put("history", "History");
    commandKeys.put("pushd", "PopD");

    while (entry.startsWith(" ")) {
      entry = entry.substring(1);
    }

    String[] splitEntry = entry.split(" ");
    System.out.println(Arrays.toString(splitEntry));
    System.out.println(splitEntry[0]);

    if (splitEntry.length <= 1) {
      try {
        String commandName = singleCommandKeys.get(splitEntry[0]);
        System.out.println(commandName);
        try {
          Class commandClass = Class.forName(commandName);
          CommandInterface commandInstance =
              (CommandInterface) commandClass.newInstance();
          commandInstance.execute();

        } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          System.out.println("Invalid command1");
        } catch (InstantiationException e) {
          // TODO Auto-generated catch block
          // e.printStackTrace();
          System.out.println("Invalid command2");
        } catch (IllegalAccessException e) {
          // TODO Auto-generated catch block
          // e.printStackTrace();
          System.out.println("Invalid command3");
        }
      } catch (NullPointerException e) {
        System.out.println("Invalid command0");
      }

    } else {
      try {
        String commandName = commandKeys.get(splitEntry[0]);
        String[] commandParameters = Arrays.copyOfRange(splitEntry, 1, splitEntry.length);
        System.out.println(commandName);
        try {
          Class commandClass = Class.forName(commandName);
          CommandInterface commandInstance = CommandInterface.class.getConstructor(String[].class).newInstance(commandParameters);
          commandInstance.execute();
          
        } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          System.out.println("Invalid command1");
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
        } catch (InstantiationException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      } catch (NullPointerException e) {
        System.out.println("Invalid command0");
      }
    }

  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    String teststring = "                    this is a test      ";
    while (teststring.startsWith(" ")) {
      teststring = teststring.substring(1);
    }
    /*
     * String [] test = teststring.split(" ");
     * System.out.println(Arrays.toString(test));
     * System.out.println(test.length); System.out.println(test[0].length());
     * System.out.println("this   is a test".substring(1)); System.out.println(
     * "              this is a test".startsWith(" "));
     */
    ProQuery.sortQuery("ikshgdk");
    System.out.println("this   is a test".substring(1));
  }

}
