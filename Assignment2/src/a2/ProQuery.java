package a2;

import java.util.Arrays;
import java.util.Hashtable;

public class ProQuery {

  public static void sortQuery(String entry) {
    
    Hashtable<String, String> singleCommandKeys =
        new Hashtable<String, String>();
    
    Hashtable<String, String> commandKeys =
        new Hashtable<String, String>();

    singleCommandKeys.put("ls", "LS");
    singleCommandKeys.put("exit", "Exit");
    singleCommandKeys.put("pwd", "PWD");
    singleCommandKeys.put("history", "History");
    singleCommandKeys.put("popd", "PushD");

    commandKeys.put("cd", "CD");
    commandKeys.put("cat", "Cat");
    commandKeys.put("echo", "");
    commandKeys.put("ls", "LS");
    commandKeys.put("man", "Man");
    commandKeys.put("mkdir", "Mkdir");
    commandKeys.put("history", "History");
    commandKeys.put("pushd", "PopD");
    
    while(entry.startsWith(" ")) {
      entry = entry.substring(1);
    }
    
    String[] splitEntry = entry.split(" ");
    System.out.println(Arrays.toString(splitEntry));
    System.out.println(splitEntry[0]);
    
    if (splitEntry.length <= 1) {
        String commandName = singleCommandKeys.get(splitEntry[0]);
        System.out.println(commandName);
        try {
          Class commandClass = Class.forName(commandName);
          Object commandInstance = commandClass.newInstance();
          
        } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          // e.printStackTrace();
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
        
    }
    
  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    String teststring = "                    this is a test      ";
    while(teststring.startsWith(" ")) {
      teststring = teststring.substring(1);
    }
    /* String [] test = teststring.split(" ");
    System.out.println(Arrays.toString(test));
    System.out.println(test.length);
    System.out.println(test[0].length());
    System.out.println("this   is a test".substring(1));
    System.out.println("              this is a test".startsWith(" ")); */
    ProQuery.sortQuery("exit");
    System.out.println("this   is a test".substring(1));
  }

}
