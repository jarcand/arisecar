package guard;

public class Guard {
	
	public static final String DefaultName = "Gudra";
	
	private final GuardClient client;
	private final GuardMessageControl messageControl;
	private final GuardControl control;
	
	
	public Guard(){
		client = new GuardClient(this);
		messageControl = new GuardMessageControl(this);
		control = new GuardControl(this);
		
	}
	
	public GuardClient getClient(){
		return client;
	}
	
	public GuardMessageControl getMessageControl(){
		return messageControl;
	}
	
	public GuardControl getControl(){
		return control;
	}
	
	public static void main(String [] args){
		new Guard();
	}

}
