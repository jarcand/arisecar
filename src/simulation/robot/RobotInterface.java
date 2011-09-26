package simulation.robot;

import network.message.Message;

public interface RobotInterface {
	
	public String getName();
	public void handleMessage(Message message);

}
