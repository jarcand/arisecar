package ros;


public class RosNode {
	
	private final RosCore core;
	
	public RosNode(RosCore core){
		this.core = core;
	}
	
	public void sendMessage(Object message, int id){
		core.sendMessage(message, id);
	}
	
	public void receiveMessage(Object message){
		
	}
	
}
