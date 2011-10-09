package network.client;

import java.io.IOException;

import com.lloseng.ocsf.client.AbstractClient;

import user.ControlCenter;
import util.Log;
import network.message.Message;
import network.message.MessageCreator;

/**
 * 
 * @author Gudradain
 *
 */
public class ControlClient extends AbstractClient{
	
	private final ControlCenter controlCenter;

	public ControlClient(ControlCenter controlCenter){
		this("localhost", 5555, controlCenter);
	}

	public ControlClient(int port, ControlCenter controlCenter){
		this("localhost", port, controlCenter);
	}

	public ControlClient(String host, int port, ControlCenter controlCenter){
		super(host, port);
		this.controlCenter = controlCenter;
		try {
			openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Override
	public void sendToServer(Object msg){
		for(String robotName : controlCenter.getRobotNameList()){
			Message message = MessageCreator.createMessage(msg, robotName, false);
			try {
				super.sendToServer(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		Log.println("problem with connection");
		exception.printStackTrace();
	}

	/**
	 * Hook method called after a connection has been established.
	 */
	protected void connectionEstablished(){
		Log.println("Client connected");
		sendToServer("Hello");
	}

	/**
	 * Handles a message sent from the server to this client.
	 *
	 * @param msg   the message sent.
	 */
	protected void handleMessageFromServer(Object msg){
		if(msg instanceof Message){
			Message message = (Message) msg;
			if(message.isFromRobot()){
				if(controlCenter.getRobotNameList().contains(message.getRobotName())){
					controlCenter.handleMessage(message);
				}
			}
		}
	}
}
