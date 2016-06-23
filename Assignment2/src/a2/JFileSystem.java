package a2;

import java.util.Vector;
import java.util.*;
public class JFileSystem implements FileSystem{
  //Output collaboration
   // DirStack collaboration
   // A vector which holds all Directories at root
   //private Vector<Folder> Manager;
   // A vector that holds all the full paths of each entity in JShell
   private Vector<String> fullPaths;
   // A string representing the current working directory
   private String currDir;
   // The current working folder
   private Folder currFolder;
   private Folder rootFolder;
   private DirStack dirStack;
  
   /**
    * The constructor
    */
   public JFileSystem() {
     //this.Manager = new Vector();
     this.fullPaths = new Vector();
     // Adding the two most basic paths
     this.fullPaths.addElement("/");
     // The root directory
     this.currDir = "/";
   }
  
   /**
    * This function return the path of the current working directory.To be used
    * with various command classes.
    * 
    * @return currDirr - The absolute path of the current working directory
    */
   public String getCurrPath() {
     return this.currDir;
   }
  
   /**
    * This method accepts an absolute path and sets that as the current working
    * directory. To only be used by CD
    * 
    * @param newDir - Represents the new absolute path for the new working
    *        directory
    */
   public void setFullPath(String newDir) {
     this.currDir = newDir;
   }
  
   /**
    * This function gets one of the Directories present at root. It takes in an
    * index position as a parameter.To be used with various command classes.
    * 
    * @param index - An integer representing an index position
    * @return returns the directory at the specified index of Manager
    */
   /*public Object getObject(int index) {
     if (index >= Manager.size()){
       return null;
     }
     return Manager.get(index);
   }*/
  
   /**
    * This function accepts an absolute path or a name of an object in the local
    * directory and returns the corresponding directory or File according the the
    * path. Traverses through the all the Trees that exist at root. To be used
    * with various command classes.
    * 
    * @param name - A string representing an absolute path or a name of a local
    *        directory/file
    * @return - An object at the location of the specified absolute path
    */
   public Object getObject(String name) {
     // The object that is to be returned
     Object result = null;
     // If an absolute path is provided
     if (name.startsWith("/")) {
       result = getObjRecurs(name, "/", this.rootFolder);
       // A loop which checks each tree present at root
       /*for (int i = 0; i < Manager.size(); i++) {
         // Only runs if the object is not found
         if (result == null) {
           // Sends the given absolute path, the initial path of the current
           // tree, and the head of the tree
           result = getObjRecurs(name, "/" + ((Folder) Manager.get(i)).getName(),
               Manager.get(i));
         }
       }*/
  
     } else { // If a local directory name is given
       // turning the local directory name into an absolute path
       name = this.currDir + "/" + name;
       result = getObjRecurs(name, "/", this.rootFolder);
       /*for (int i = 0; i < Manager.size(); i++) {
         if (result == null) {
           result = getObjRecurs(name, "/" + ((Folder) Manager.get(i)).getName(),
               Manager.get(i));
         }
       }*/
     }
     return result;
   }
  
   /**
    * This is the recursive helper function to getObject() which searches through
    * the tree. With each recursive step it checks a new child, and adds to the
    * previous path until either, the desired object is found or the end of the
    * subtree is reached.
    * 
    * @param name - The absolute path of the desired directory or file
    * @param currName - The current path built by the previous recursive steps
    * @param dirrOrFile
    * @return result - Can either be the desired object or a null type
    */
   public Object getObjRecurs(String name, String currName, Folder dirrOrFile) {
     // Base case if the desired name matches the name built by the recursive
     // steps
     if (currName.equals(name)) {
       return dirrOrFile;
     }
     // If the end of the subtree is reached
     else if (dirrOrFile == null) {
       Object result = null;
     }
     // A vector representing all the children in the tree
     Vector allChildren = dirrOrFile.getAllChildren();
     // Object representing the result of the recursive step
     Object result = null;
     // Runs for all children of the node and while an object is not found and
     // iff the node has children
     for (int i = 0; result == null && allChildren != null
         && i < allChildren.size(); i++) {
       // If the specified child is a Folder
       if (allChildren.get(i).getClass().equals(Folder.class)) {
         // the recursive call on the specified child as well as adding onto
         // the current path
         if (currName.equals("/")){
           result = getObjRecurs(name, "/" + ((Folder) allChildren.get(i)).getName(), (Folder) allChildren.get(i));
         }
         else{
           result = getObjRecurs(name,
             currName + "/" + ((Folder) allChildren.get(i)).getName(),
             (Folder) allChildren.get(i));
         }
         
       }
       // If the specified child is a File
       else {
         // Sending in a null as the child since, files have no children
         if (currName.equals("/")){
           result = getObjRecurs(name,
               "/" + ((File) allChildren.get(i)).getName(), null);
         }
         else{
           result = getObjRecurs(name,
               currName + "/" + ((File) allChildren.get(i)).getName(), null);
         }
         
       }
  
     }
     return result;
   }
  
   /**
    * Adds a folder at ROOT only into Manager, as well as its corresponding
    * absolute path. To be used only with Mkdir.
    * 
    * @param newFolder - A Folder at root
    */
   public void add(Folder newFolder) {
     //Manager.add(newFolder);
     fullPaths.add(newFolder.getPath());
   }
  
   /**
    * Adds a file's absolute path into fullPaths. To be used only with
    * EchoOverride.
    * 
    * @param newFolder - A File
    */
   public void addFilePath(String path) {
     // Manager.addElement(newFile); //To be possibly removed later
     fullPaths.add(path);
   }
  
   /**
    * Returns a vector contained all the absolute paths in JShell
    * 
    * @return A vector containing all the absolute paths in JShell
    */
   public Vector<String> getFullPaths() {
     return this.fullPaths;
   }
  
   /**
    * Checks whether the specified absolute path is valid and exist or not.
    * 
    * @param path - An absolute path
    * @return A boolean representing whether or not the path is valid
    */
   public boolean checkValidPath(String path) {
     return this.fullPaths.contains(path);
   }
  
   /**
    * Sets the current working folder. To be used by CD only.
    * 
    * @param folder - representing the new current working folder
    */
   public void setCurrFolder(Folder folder) {
     this.currFolder = folder;
   }
   public void setRoot(Folder root){
     this.rootFolder = root;
   }
  
   /**
    * returns the current working folder
    * 
    * @return the current working folder
    */
   public Folder getCurrFolder() {
     return this.currFolder;
   }
   public Folder getRootFolder() {
     return this.rootFolder;
   }
   /**
    * Adds the an absolute path to fullPaths. To be used only in Mkdir for
    * non-root Folders.
    * 
    * @param path - An absolute path of a Folder
    */
   public void addFullPath(String path) {
     this.fullPaths.addElement(path);
   }
   
   /**
    * This function checks if the relative path is a valid one, by seeing if
    * it's a substring of another larger path
    * 
    * @param path - A relative path relative to the current working directory
    * @return A boolean representing validity
    */
   public boolean relativePathChecker(String path) { 
     boolean valid = false;
     for (int i = 0; i < this.fullPaths.size(); i++) {
       if (this.fullPaths.get(i).contains(path)) {
         valid = true;
       }
     }
     return valid;
   }
   public DirStack getDirStack()
   {
     return this.dirStack;
   }
   public void setDirStack(DirStack stack){
     this.dirStack = stack;
   }
}
