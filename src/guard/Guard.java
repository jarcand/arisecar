package guard;

public class Guard {
	
	public static final String DefaultName = "Gudra";
	
	private final GuardClient client;
	private final GuardMessageControl messageControl;
	private final GuardGUI gui;
	
	
	public Guard(){
		client = new GuardClient(this);
		messageControl = new GuardMessageControl(this);
		gui = new GuardGUI(this);
	}
	
	public GuardClient getClient(){
		return client;
	}
	
	public GuardMessageControl getMessageControl(){
		return messageControl;
	}
	
	public GuardGUI getGUI(){
		return gui;
	}
	
	public static void main(String [] args){
		new Guard();
	}

}
