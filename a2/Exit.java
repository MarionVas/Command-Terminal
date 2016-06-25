package a2;

public class Exit implements CommandInterface {
    private FileSystem manager;
    public Exit(JFileSystem man){
      this.manager = man;
      
    }
	public void execute() {
		// method to exit consoles
		System.exit(0);
	}
	
	/**
	   * This function return the instructions on how to use the command exit.
	   * 
	   * @return a string telling users the how the command works
	   */
	
	public String manual(){
	  return "exit - Terminates the session/program.\n";
	}

}
