package a2;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

public class Man implements CommandInterface {

  private JFileSystem jFileSystem;
  private String[] commandKey;

  public Man(JFileSystem jFileSystem, String[] commandKey) {
    this.jFileSystem = jFileSystem;
    this.commandKey = commandKey;
  }

  public void execute() {

    Hashtable<String, String> commandNoPara = new Hashtable<String, String>();

    Hashtable<String, String> commandWithPara = new Hashtable<String, String>();

    commandNoPara.put("ls", "a2.LS");
    commandNoPara.put("exit", "a2.Exit");
    commandNoPara.put("pwd", "a2.PWD");
    commandNoPara.put("popd", "a2.PopD");

    commandWithPara.put("cd", "a2.CD");
    commandWithPara.put("cat", "a2.Cat");
    commandWithPara.put("echo", "a2.Echo");
    commandWithPara.put("man", "a2.Man");
    commandWithPara.put("mkdir", "a2.Mkdir");
    commandWithPara.put("pushd", "a2.PushD");

    if (commandKey.length == 1 && commandNoPara.containsKey(commandKey[0])) {

      String commandName = commandNoPara.get(commandKey[0]);

      try {
        CommandInterface commandInstance =
            (CommandInterface) Class.forName(commandName)
                .getConstructor(JFileSystem.class).newInstance(jFileSystem);
        Output.printString(commandInstance.manual());

      } catch (InstantiationException | IllegalAccessException
          | IllegalArgumentException | InvocationTargetException
          | NoSuchMethodException | SecurityException
          | ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } else if (commandKey.length == 1
        && commandWithPara.containsKey(commandKey[0])) {
      
      String commandName = commandWithPara.get(commandKey[0]);
      
      try {
        CommandInterface commandInstance =
            (CommandInterface) Class.forName(commandName)
                .getConstructor(JFileSystem.class, String[].class)
                .newInstance(jFileSystem, commandKey);
        Output.printString(commandInstance.manual());
        
      } catch (InstantiationException | IllegalAccessException
          | IllegalArgumentException | InvocationTargetException
          | NoSuchMethodException | SecurityException
          | ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
    } else {
      Output.printError();
    }
  }

  public String manual() {
    return "man CMD - Displays documentation regarding the specified command\n";
  }
}
