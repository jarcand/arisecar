package network.robot;

import java.io.IOException;


import com.lloseng.ocsf.client.AbstractClient;

import network.message.MessageRobot;
import network.message.MessageUser;

public class RobotClient extends AbstractClient{
	
	private final Robot robot;
	private final RobotMessageControl messageControl;
	
	public RobotClient(Robot robot){
		this("localhost", 5555, robot);
	}

	public RobotClient(int port, Robot robot){
		this("localhost", port, robot);
	}

	public RobotClient(String host, int port, Robot robot){
		super(host, port);
		this.robot = robot;
		messageControl = new RobotMessageControl(this, robot);
		try {
			openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendToServer(Object msg){
		MessageRobot message = new MessageRobot(robot.getName(), msg);
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
		if(msg instanceof MessageUser){
			MessageUser message = (MessageUser)msg;
			if(message.getUser().equals(robot.getName())){
				messageControl.handleMessage(message.getObject());
			}
		}
	}

}
