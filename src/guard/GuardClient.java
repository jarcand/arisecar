package guard;

import java.io.IOException;
import networking.KeyboardMovement;
import networking.Message;
import networking.MessageFactory;
import networking.XboxMovement;

import com.lloseng.ocsf.client.AbstractClient;


public class GuardClient extends AbstractClient {

	//public static String HOST = "192.168.3.103";
	public static String HOST = "localhost";
	
	private final Guard guard;

	public GuardClient(Guard guard){
		this(HOST, 5555, guard);
	}

	public GuardClient(int port, Guard guard){
		this(HOST, port, guard);
	}

	public GuardClient(String host, int port, Guard guard){
		super(host, port);
		this.guard = guard;
		try {
			openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("- Client establishing connection with " + host + ":" + port);
	}
	
	public void sendToRobot(KeyboardMovement msg, String robotName){
		Message message = MessageFactory.createKeyboardMovement(msg, robotName);
		sendToServer(message);
	}
	
	public void sendToRobot(XboxMovement msg, String robotName){
		Message message = MessageFactory.createXboxMovement(msg, robotName);
		sendToServer(message);
	}

	@Override
	public void sendToServer(Object msg){
		try {
			super.sendToServer(msg);
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
			if(message.isToClient()){
				guard.getMessageControl().handleMessage(message);
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
		System.out.println("- Connection established with " + getHost() + ":" + getPort());
	}


}
