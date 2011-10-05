package network.robot;

import hal.HAL;

public abstract class Robot {
	
	private final String name;
	private final RobotClient robotClient;
	
	public Robot(String name){
		this.name = name;
		robotClient = new RobotClient(this);
	}

	public String getName(){
		return name;
	}
	
	public abstract HAL getHAL();

}
