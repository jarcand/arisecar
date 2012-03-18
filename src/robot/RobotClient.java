package robot;

import java.io.IOException;
import networking.Message;
import networking.MessageFactory;
import ca.ariselab.utils.LoopingThread;
import com.lloseng.ocsf.client.AbstractClient;


public class RobotClient extends AbstractClient {
	
	private final String name;
	private final RobotMessageControl msgCtrl;
	private final VehicleModel v;
	
	public RobotClient(String host, int port, String robot){
		super(host, port);
		name = robot;
		v = new VehicleModel();
		msgCtrl = new RobotMessageControl(v);
		
		System.out.println("- Client establishing connection with " + host + ":" + port);
		try {
			openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("- Connection established");
		(new LoopingThread("robot updates", 0, 100) {
            protected void mainLoop() {
            	v.update();
            	Message message = MessageFactory.createVehicleUpdate(getName(), v);
            	try {
	                sendToServer(message);
                } catch (IOException e) {
	                e.printStackTrace();
                }
            }
		}).start();
	}
	
	public String getName() {
		return name;
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
				if(message.getRobotName().equals(getName())){
					msgCtrl.handleMessage(message);
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
		System.out.println("problem with connection");
		exception.printStackTrace();
	}

	/**
	 * Hook method called after a connection has been established.
	 */
	@Override
	protected void connectionEstablished(){
		System.out.println("- Connection established with " + getHost() + ":" + getPort());
	}

	public static void main(String[] args) {
		new RobotClient("localhost", 5555, "Gudra");
	}
}
