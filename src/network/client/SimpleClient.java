package network.client;

import java.io.IOException;

import network.message.Message;
import network.message.Position;


import com.lloseng.ocsf.client.*;

/**
 * The <code> SimpleClient </code> class is a simple subclass
 * of the <code> ocsf.server.AbstractClient </code> class.
 * It allows testing of the functionalities offered by the
 * OCSF framework. The <code> java.awt.List </code> instance
 * is used to display informative messages.
 * This list is
 * pink when the connection has been closed, red
 * when an exception is received,
 * and green when connected to the server.
 *
 * @author Dr. Robert Lagani&egrave;re
 * @version February 2001
 * @see ocsf.server.AbstractServer
 */
public class SimpleClient extends AbstractClient{
	
	private final ClientMap map;

	public SimpleClient(ClientMap map){
		this("localhost", 5555, map);
	}

	public SimpleClient(int port, ClientMap map){
		this("localhost", port, map);
	}

	public SimpleClient(String host, int port, ClientMap map){
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
		if(msg instanceof Position){
			map.addPosition((Position)msg);
		}
	}
}
