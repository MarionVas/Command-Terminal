package a2;

import java.util.*;

public class Mkdir {
  private FileSystem Manager;
  private String[] specialChar =
      new String[] {"/", "!", "@", "$", "&", "#", "*", "(", ")", "?", ":", "[",
          "]", "\"", "<", ">", "\'", "`", "\\", "|", "=", "{", "}", "/", ";"};

  public Mkdir(FileSystem fileManager) {
    this.Manager = fileManager;
  }

  public void create(String name) {
    if (name.startsWith("/")) {
      Vector<String> fullPaths = Manager.getFullPaths();
      if (fullPaths.contains(name)) {
        int currDirIndex = name.lastIndexOf("/");
        String currDir = name.substring(currDirIndex + 1, name.length());
        Folder newFolder = new Folder(currDir, name);
        Manager.add(newFolder);
        String parentPath = name.substring(0, name.lastIndexOf("/"));
        int nameIndex = parentPath.lastIndexOf("/");
        String parentName =
            parentPath.substring(nameIndex, parentPath.length());
        for (int i = 0; i < Manager.getFullPaths().size(); i++){
          Folder parentFolder = (Folder) Manager.getObject(i);
          if (parentName == parentFolder.getName()){
            parentFolder.addChildren(newFolder);
          }
        }
        //System.out.println(currDir + "    " + name);
      } else {
        // Return an error
      }
    } 
    else {
      boolean valid = true;
      for (int i = 0; i < specialChar.length; i++){
        if (name.contains(specialChar[i])){
          valid = false;
        }
      }
      if (valid){
        String fullPath = Manager.getCurrPath() + "/" + name;
        Folder newFolder = new Folder(name, fullPath);
        Manager.add(newFolder);
        int nameIndex = Manager.getCurrPath().lastIndexOf("/");
        String parentName = Manager.getCurrPath().substring(nameIndex + 1, 
            Manager.getCurrPath().length());
        for (int i = 0; i < Manager.getFullPaths().size(); i++){
          Folder parentFolder = (Folder) Manager.getObject(i);
          if (name == parentFolder.getName()){
            parentFolder.addChildren(newFolder);
          }
        }
      }
      else {
        //Return an error message
      }
    }
  }
}
