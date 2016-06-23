package a2;

import java.util.*;

public interface FileSystem {
  public String getCurrPath(); 
  public void setFullPath(String newDir);
  public Object getObject(String name);
  public Object getObjRecurs(String name, String currName, Folder dirrOrFile);
  public void add(Folder newFolder);
  public void addFilePath(String path);
  public Vector<String> getFullPaths();
  public boolean checkValidPath(String path);
  public void setCurrFolder(Folder folder);
  public Folder getCurrFolder();
  public void addFullPath(String path);
  public boolean relativePathChecker(String path);
  public void setRoot(Folder root);
  public Folder getRootFolder();
  public void setDirStack(DirStack stack);
  public void setHistory(History hist);
}


