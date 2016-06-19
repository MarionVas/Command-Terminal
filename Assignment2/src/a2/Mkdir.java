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
      //Vector<String> fullPaths = Manager.getFullPaths();
      String parentPath = name.substring(0, name.lastIndexOf("/"));
      // System.out.println(parentPath + "test1");
      if (Manager.checkValidPath(parentPath)) {
        // System.out.println(name + "test1");
        int currDirIndex = name.lastIndexOf("/");
        String currDir = name.substring(currDirIndex + 1, name.length());
        //System.out.println(currDir + "asdasd");
        Folder newFolder = new Folder(currDir, name);
        Manager.addFullPath(name);
        // parentPath = name.substring(0, name.lastIndexOf("/"));
        if (name.split("/").length > 2) {
          Folder parentFolder = (Folder) Manager.getObject(parentPath);
          parentFolder.addChildren(newFolder);
        }
        else{
          Manager.add(newFolder);
          newFolder.isAtRoot(true);
        }
        /*
         * int nameIndex = parentPath.lastIndexOf("/"); String parentName =
         * parentPath.substring(nameIndex, parentPath.length()); for (int i = 0;
         * i < Manager.getFullPaths().size(); i++){ Folder parentFolder =
         * (Folder) Manager.getObject(i); if (parentName ==
         * parentFolder.getName()){ parentFolder.addChildren(newFolder); } }
         */ // To be removed
        // System.out.println(currDir + " " + name); //To be removed
      } else {
        // Return an error
      }
    } 
    else if (name.startsWith("..")){
      int lastIndex = 0;
      int count = 0;

      while(lastIndex != -1){

          lastIndex = name.indexOf("..",lastIndex);

          if(lastIndex != -1){
              count ++;
              lastIndex += 2;
          }
      }
      String path = "";
      for (int i = 1; i < Manager.getCurrPath().split("/").length - count; i++){
        path =  path + "/" +  Manager.getCurrPath().split("/")[i];
      }
      name = path + name;
      String parentPath = name.substring(0, name.lastIndexOf("/"));
      // System.out.println(parentPath + "test1");
      if (Manager.checkValidPath(parentPath)) {
        // System.out.println(name + "test1");
        int currDirIndex = name.lastIndexOf("/");
        String currDir = name.substring(currDirIndex + 1, name.length());
        //System.out.println(currDir + "asdasd");
        Folder newFolder = new Folder(currDir, name);
        Manager.addFullPath(name);
        // parentPath = name.substring(0, name.lastIndexOf("/"));
        if (name.split("/").length > 2) {
          Folder parentFolder = (Folder) Manager.getObject(parentPath);
          parentFolder.addChildren(newFolder);
        }
        else{
          Manager.add(newFolder);
          newFolder.isAtRoot(true);
        }
        /*
         * int nameIndex = parentPath.lastIndexOf("/"); String parentName =
         * parentPath.substring(nameIndex, parentPath.length()); for (int i = 0;
         * i < Manager.getFullPaths().size(); i++){ Folder parentFolder =
         * (Folder) Manager.getObject(i); if (parentName ==
         * parentFolder.getName()){ parentFolder.addChildren(newFolder); } }
         */ // To be removed
        // System.out.println(currDir + " " + name); //To be removed
      } else {
        // Return an error
      }
    }
    else if (name.contains("/")){
      name = Manager.getCurrPath() + name;
      
      
      String parentPath = name.substring(0, name.lastIndexOf("/"));
      // System.out.println(parentPath + "test1");
      if (Manager.checkValidPath(parentPath)) {
        // System.out.println(name + "test1");
        int currDirIndex = name.lastIndexOf("/");
        String currDir = name.substring(currDirIndex + 1, name.length());
        //System.out.println(currDir + "asdasd");
        Folder newFolder = new Folder(currDir, name);
        Manager.addFullPath(name);
        // parentPath = name.substring(0, name.lastIndexOf("/"));
        if (name.split("/").length > 2) {
          Folder parentFolder = (Folder) Manager.getObject(parentPath);
          parentFolder.addChildren(newFolder);
        }
        else{
          Manager.add(newFolder);
          newFolder.isAtRoot(true);
        }
        /*
         * int nameIndex = parentPath.lastIndexOf("/"); String parentName =
         * parentPath.substring(nameIndex, parentPath.length()); for (int i = 0;
         * i < Manager.getFullPaths().size(); i++){ Folder parentFolder =
         * (Folder) Manager.getObject(i); if (parentName ==
         * parentFolder.getName()){ parentFolder.addChildren(newFolder); } }
         */ // To be removed
        // System.out.println(currDir + " " + name); //To be removed
      } else {
        // Return an error
      }
    }
    else {
      boolean valid = true;
      for (int i = 0; i < specialChar.length; i++) {
        if (name.contains(specialChar[i])) {
          valid = false;
        }
      }
      if (valid) {
        String fullPath = "";
        if (Manager.getCurrPath().split("/", -1).length <= 2) {
          System.out.println(Manager.getCurrPath().split("/", -1).length);
          fullPath = Manager.getCurrPath() + name;
        }

        else {
          fullPath = Manager.getCurrPath() + "/" + name;
        }
        Folder newFolder = new Folder(name, fullPath);
        Manager.addFullPath(fullPath);
        // System.out.println("added2");
        if (fullPath.split("/").length > 2) {
          Folder parentFolder =
              (Folder) Manager.getObject(Manager.getCurrPath());
          parentFolder.addChildren(newFolder);
        }
        else{
          Manager.add(newFolder);
          newFolder.isAtRoot(true);
        }

        /*
         * int nameIndex = Manager.getCurrPath().lastIndexOf("/"); String
         * parentName = Manager.getCurrPath().substring(nameIndex + 1,
         * Manager.getCurrPath().length()); for (int i = 0; i <
         * Manager.getFullPaths().size(); i++){ Folder parentFolder = (Folder)
         * Manager.getObject(i); if (name == parentFolder.getName()){
         * parentFolder.addChildren(newFolder); } }
         */ // To be removed
      } else {
        // Return an error message //To be removed
      }
    }
  }
}
