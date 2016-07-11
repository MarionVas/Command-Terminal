package a2;

import java.util.Vector;

public class Grep 
{
  private JFileSystem fileManager;
  private String[] command;
  private String output = "";
  private boolean recurse = false;
  private String regex;
  private String path;

  public Grep(JFileSystem fileManager, String[] command)
  {
    this.fileManager = fileManager;
    this.command = command;
    // retrieve the path
    this.path = command[command.length - 1];
  }
  
  public void execute()
  {
    //System.out.println(fileManager.checkValidPath(path) + "asdas");

    // Change to absolute path if not already a full path
    if (!fileManager.getCurrPath().equals("/")) 
    {
      path = fileManager.getCurrPath() + "/" + path;
    } 
    // if it is a full path, just add the / to make it absolute
    else
    {
      if (path.charAt(0) != '/')
      {
        path = "/" + path;
      }
    }

    // Check if the given path is valid
    if (!fileManager.checkValidPath(path))
    {
      // display error if the path isn't valid
      Output.printPathError();
    }
    // 
    else
    {
      // Check for appearance of -r or -R
      if (command[0] == "-r" || command[0] == "-R")
      {
        // set regex as the 2nd param
        regex = command[1];
        
        // Check if the given path is a directory
        
        // If the end of the subtree is reached
//        String result = "";
//        
//        if (dirrOrFile == null || dirrOrFile.equals(File.class)) 
//        {
//          result = "";
//        }
//        else 
//        {
//          Vector<Item> allChildren = dirrOrFile.getChildren();
//          if (allChildren != null && allChildren.size() != 0) {
//            if (childIndex == 0) {
//              result = this.executeFullPath(((Folder) dirrOrFile).getPath(), true)
//                  + this.recurseLS(childIndex, allChildren.get(childIndex))
//                  + this.recurseLS(childIndex + 1, dirrOrFile);
//            } else if (childIndex < allChildren.size()) {
//              result = this.recurseLS(0, allChildren.get(childIndex))
//                  + this.recurseLS(childIndex + 1, dirrOrFile);
//            }
//          } 
//          else
//          {
//            result = this.executeFullPath(((Folder) dirrOrFile).getPath(), true);
//          }
//        }
      }
      
      // If there is no -r or -R statement
      else
      {
        output = grepNoRecurse();
        System.out.println(output);
      }
    }
  }
  
  public String grepNoRecurse()
  {
    
    regex = command[0];

    // Create a folder 
    //Folder currFolder = fileManager.getRootFolder();
    
    // get the file at the given path
    File file = (File) fileManager.getObject(path);
    
    // check if the file exists
    if (file == null) 
    {
      // if the file doesn't exist, print an error
      Output.printFileNameError();
    } 
    // if the file exists, scan for regex in the file and print all
    // lines containing it
    else
    {
      // get the contents of the file
      String fileContents = file.getBody();

      // split the file based on newlines
      String[] splitContents = fileContents.split("\\n");
      
      // scan through every line
      for (int i = 0; i < splitContents.length; i++)
      {
        // if the line contains the given regex, add it to the output
        if (splitContents[i].contains(regex))
        {
          output = output + splitContents[i] + "\n";
        }
      }
      return output;
    }
    return output;
  }

}
