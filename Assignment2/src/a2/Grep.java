package a2;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep implements CommandInterface
{
  private JFileSystem fileManager;
  private String[] command;
  private String output = "";
  private boolean recurse = false;
  private String regex;
  private String path;
  private Pattern search;
  private Matcher matcher;
  private Item dirOrFile;

  public Grep(JFileSystem fileManager, String[] command)
  {
    this.fileManager = fileManager;
    this.command = command;
    // retrieve the path
    this.path = command[command.length - 1];
  }
  
  public String execute()
  {
    try {
      // make sure the path is a full path - also checks if the path is valid
      this.path = fileManager.getFullPath(path);
      System.out.println("THE FIRST PARAM IS" + command[0]);
      // Check for appearance of -r or -R
      if (command[0].equals("-r") || command[0].equals("-R"))
      {
        System.out.println("IT MADE IT HERE WTF FUCK");
        // set regex as the 2nd param
        this.regex = command[1];
        System.out.println(regex);
        
        this.dirOrFile = fileManager.getObject(path);
        
        if (!this.dirOrFile.getClass().equals(Folder.class))
        {
          System.err.println("This was not a directory");
        }
        else
        {
          this.output = grepRecurse(0, this.fileManager.getObject(path));
        }
      }

      // If there is no -r or -R statement
      else
      {
        // get the object at the given path
        dirOrFile = fileManager.getObject(path);

        // check if the object is a file
        if (!dirOrFile.getClass().equals(File.class)) 
        {
          // if the file doesn't exist, print an error
          Output.printFileNameError();
        }
        // If it is a file
        else
        {
          // get the given regex at this location if -R isn't supplied
          regex = command[0];
          // Get the lines in the given file that ocntain the regex
          this.output = grepNoRecurse((File)dirOrFile);
          //System.out.println(this.output);
        }
      }
    } catch (InvalidPath e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
    return this.output;
  }
  
  public String grepNoRecurse(File file)
  {
    String matchedContents = "";
    // get the contents of the file
    String fileContents = file.getBody();

    // split the file based on newlines
    String[] splitContents = fileContents.split("\\n");
 
    // make a pattern object that uses the given regex
    this.search = Pattern.compile(this.regex);
    // loops through the array 
    for (int i = 0; i < splitContents.length; i++)
    {
      System.out.println(splitContents[i]);
      // make matcher object
      this.matcher = this.search.matcher(splitContents[i]);
      // if the line matches the regex
      if (this.matcher.find())
      {
        // add to output
        matchedContents += (splitContents[i] + "\n");
      }
    }
    return matchedContents;
  }
  
  public String grepRecurse(int childIndex, Item dirrOrFile) {
    // If the end of the subtree is reached
    String result = "";

    // If the item does not exist, do nothing
    if (dirrOrFile == null)
    {
      result = "";
    }
    // If the item is a file, add to the output
    else if (dirrOrFile.getClass().equals(File.class))
    {
      // get the path of the file
      String filePath = dirOrFile.getPath();
      // get the name of the file
      String fileName = dirOrFile.getName();
      // append to the output
      result += filePath + "/" + fileName +
          ":\n" + grepNoRecurse((File)dirrOrFile);
      //System.out.println(result);
    }
    // if the item is a directory/folder
    else {
      // Collect the children of the folder
      Vector<Item> allChildren = dirrOrFile.getChildren();
      // If the folder contains items
      if (allChildren != null && allChildren.size() != 0) 
      {
        // Check the first item inside the folder
        if (childIndex == 0) 
        {
          // Check all of its children
          result = result 
              + this.grepRecurse(childIndex, allChildren.get(childIndex))
              + this.grepRecurse(childIndex + 1, dirrOrFile);
        }
        // Check the rest of the items in the folder
        else if (childIndex < allChildren.size())
        {
          // Check all of their children
          result = this.grepRecurse(0, allChildren.get(childIndex))
              + this.grepRecurse(childIndex + 1, dirrOrFile);
        }
      }
    }
    return result;
  }

  @Override
  public String manual() {
    // TODO Auto-generated method stub
    return null;
  }

}
