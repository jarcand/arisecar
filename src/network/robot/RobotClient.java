package network.robot;

import java.io.IOException;

import com.lloseng.ocsf.client.AbstractClient;

import network.client.ClientMap;
import network.message.Message;
import network.message.Position;

public class RobotClient extends AbstractClient{
	
	public RobotClient(ClientMap map){
		this("localhost", 5555, map);
	}

	public RobotClient(int port, ClientMap map){
		this("localhost", port, map);
	}

	public RobotClient(String host, int port, ClientMap map){
		super(host, port);
		this.map = map;
		try {
			openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendToServer(Object msg){
		Message message = new Message("StringName", msg);
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
		if(msg instanceof Message){
			Message message = (Message)msg;
			if(message.getUser().equals(name)){
				//Do something with message.getObject();
			}
		}
		if(msg instanceof Position){
			map.addPosition((Position)msg);
		}
	}

}
