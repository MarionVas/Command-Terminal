package a2;

import java.util.*;

public interface FileSystem {
  public String getCurrPath();

  public void setFullPath(String newDir);

  public Item getObject(String name);

  public Item getObjRecurs(String name, String currName, Item dirrOrFile);

  public void add(Folder newFolder);

  public Vector<String> getFullPaths();

  public boolean checkValidPath(String path);

  public void setCurrFolder(Folder folder);

  public Folder getCurrFolder();

  public void addFullPath(String path);

  public void setRoot(Item root);

  public Item getRootFolder();

  public DirStack getDirStack();

  public void setDirStack(DirStack stack);
}


