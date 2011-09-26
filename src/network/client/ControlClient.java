package network.client;

import java.io.IOException;

import com.lloseng.ocsf.client.AbstractClient;

import user.ControlCenter;
import network.message.MessageControl;
import network.message.MessageRobot;

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
			MessageControl message = new MessageControl(robotName, msg);
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
		if(msg instanceof MessageRobot){
			MessageRobot message = (MessageRobot) msg;
			if(controlCenter.getRobotNameList().contains(message.getUser())){
				controlCenter.handleMessage(message);
			}
		}
	}
}
