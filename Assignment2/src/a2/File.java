package a2;

public class File extends FileSystem {

	String body = "";
	private String name;
	public void addToBody(String append) {
		this.body = this.body + "\n" + append;
	}

	public void setBody(String body) {
		this.body = body;
	}

  public String getName() {

    return this.name;
  }

}
