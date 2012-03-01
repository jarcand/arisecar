package robot;

public class Robot {
	
	private final String name;
	
	private final RobotClient robotClient;
	private final RobotMessageControl messageControl;
	private final RobotNode robotNode;
	
	public Robot(String name) {
		this.name = name;
		robotClient = new RobotClient(this);
		messageControl = new RobotMessageControl(this);
		robotNode = new RobotNode(this);
	}
	
	public String getName(){
		return name;
	}
	
	public RobotClient getClient(){
		return robotClient;
	}
	
	public RobotMessageControl getMessageControl(){
		return messageControl;
	}
	
	public RobotNode getNode(){
		return robotNode;
	}
	
	public static void main(String [] args){
		new Robot("Gudra");
	}

}
