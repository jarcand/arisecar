package network.robot;

import java.io.IOException;

import simulation.robot.RobotInterface;

import com.lloseng.ocsf.client.AbstractClient;
import network.message.MessageControl;
import network.message.MessageRobot;

public class RobotClient extends AbstractClient{
	
	private final RobotInterface robotInterface;
	
	public RobotClient(RobotInterface robotInterface){
		this("localhost", 5555, robotInterface);
	}

	public RobotClient(int port, RobotInterface robotInterface){
		this("localhost", port, robotInterface);
	}

	public RobotClient(String host, int port, RobotInterface robotInterface){
		super(host, port);
		this.robotInterface = robotInterface;
		try {
			openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendToServer(Object msg){
		MessageRobot message = new MessageRobot(robotInterface.getName(), msg);
		try {
			super.sendToServer(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Hook method called after the connection has been closed.
	 */
	protected void connectionClosed(){
		
	}

	/**
	 * Hook method called each time an exception is thrown by the
	 * client's thread that is waiting for messages from the server.
	 *
	 * @param exception the exception raised.
	 */
	protected void connectionException(Exception exception){
		
	}

	/**
	 * Hook method called after a connection has been established.
	 */
	protected void connectionEstablished(){

	}

	/**
	 * Handles a message sent from the server to this client.
	 *
	 * @param msg   the message sent.
	 */
	protected void handleMessageFromServer(Object msg){
		if(msg instanceof MessageControl){
			MessageControl message = (MessageControl)msg;
			if(message.getUser().equals(robotInterface.getName())){
				robotInterface.handleMessage(message);
			}
		}
	}

}
