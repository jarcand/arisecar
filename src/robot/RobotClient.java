package robot;

import java.io.IOException;

import server.Message;
import util.Log;

import com.lloseng.ocsf.client.AbstractClient;

import factory.MessageFactory;

public class RobotClient extends AbstractClient {
	
	private final Robot robot;
	
	public RobotClient(Robot robot){
		this("localhost", 5555, robot);
	}

	public RobotClient(int port, Robot robot){
		this("localhost", port, robot);
	}

	public RobotClient(String host, int port, Robot robot){
		super(host, port);
		this.robot = robot;
		try {
			openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendToServer(Object msg){
		Message message = MessageFactory.createMessage(msg, robot.getName(), Message.ToClient);
		try {
			super.sendToServer(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles a message sent from the server to this client.
	 *
	 * @param msg   the message sent.
	 */
	@Override
	protected void handleMessageFromServer(Object msg){
		if(msg instanceof Message){
			Message message = (Message)msg;
			if(message.isToRobot()){
				if(message.getRobotName().equals(robot.getName())){
					robot.getMessageControl().handleMessage(message);
				}
			}
		}
	}

	/**
	 * Hook method called after the connection has been closed.
	 */
	@Override
	protected void connectionClosed(){
		
	}

	/**
	 * Hook method called each time an exception is thrown by the
	 * client's thread that is waiting for messages from the server.
	 *
	 * @param exception the exception raised.
	 */
	@Override
	protected void connectionException(Exception exception){
		Log.println("problem with connection");
		exception.printStackTrace();
	}

	/**
	 * Hook method called after a connection has been established.
	 */
	@Override
	protected void connectionEstablished(){

	}

}
