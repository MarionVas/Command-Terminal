package a2;

import java.util.Vector;

public class File extends FileSystem {
    private String name;
	private Vector<String> body = new Vector();
	public File(String name){
		this.name = name;
	}
	
	public void addToBody(String append) {
		this.body.addElement(append);;
	}

	public void setBody(Vector<String> body) {
		this.body = body;
	}

  public String getName() {

    return this.name;
  }

}
