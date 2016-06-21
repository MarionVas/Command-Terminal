package a2;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Hashtable;

public class ProQuery {
  private JFileSystem jFileSystem;

  public ProQuery(JFileSystem jFileSystem) {
    this.jFileSystem = jFileSystem;
  }

  public void sortQuery(String entry) {

    Hashtable<String, String> singleCommandKeys =
        new Hashtable<String, String>();

    Hashtable<String, String> commandKeys = new Hashtable<String, String>();

    singleCommandKeys.put("ls", "a2.LS");
    singleCommandKeys.put("exit", "a2.Exit");
    singleCommandKeys.put("pwd", "a2.PWD");
    singleCommandKeys.put("history", "a2.History");
    singleCommandKeys.put("popd", "a2.PushD");

    commandKeys.put("cd", "a2.CD");
    commandKeys.put("cat", "a2.Cat");
    commandKeys.put("echo", "");
    commandKeys.put("ls", "a2.LS");
    commandKeys.put("man", "a2.Man");
    commandKeys.put("mkdir", "a2.Mkdir");
    commandKeys.put("history", "a2.History");
    commandKeys.put("pushd", "a2.PopD");

    while (entry.startsWith(" ")) {
      entry = entry.substring(1);
    }

    String[] splitEntry = entry.split(" ");
    //System.out.println(Arrays.toString(splitEntry));
    //System.out.println(splitEntry[0]);

    if (splitEntry.length <= 1) {
      try {
        String commandName = singleCommandKeys.get(splitEntry[0]);
        //System.out.println(commandName);
        try {
          CommandInterface commandInstance =
              (CommandInterface) Class.forName(commandName)
                  .getConstructor(JFileSystem.class).newInstance(jFileSystem);
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
      } catch (NullPointerException e) {
        System.out.println("Invalid command0");
      }

    } else {
      try {
        String commandName = commandKeys.get(splitEntry[0]);
        String[] commandParameters =
            Arrays.copyOfRange(splitEntry, 1, splitEntry.length);
        //System.out.println(commandName);
        //System.out.println(Arrays.toString(commandParameters));
        try {
          CommandInterface commandInstance =
              (CommandInterface) Class.forName(commandName)
                  .getConstructor(JFileSystem.class, String[].class)
                  .newInstance(jFileSystem, commandParameters);
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
    // String teststring = " this is a test ";

    /*
     * String [] test = teststring.split(" ");
     * System.out.println(Arrays.toString(test));
     * System.out.println(test.length); System.out.println(test[0].length());
     * System.out.println("this   is a test".substring(1)); System.out.println(
     * "              this is a test".startsWith(" "));
     */
    JFileSystem jfs = new JFileSystem();
    ProQuery pq = new ProQuery(jfs);
    pq.sortQuery("pwd");


  }

}
