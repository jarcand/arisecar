package robot;

import java.io.IOException;
import java.net.UnknownHostException;
import networking.MVInstruction;
import networking.Message;
import networking.MessageFactory;
import ca.ariselab.utils.LoopingThread;
import com.lloseng.ocsf.client.AbstractClient;


public class RobotClient extends AbstractClient {
	
	private static final String MV_HOST = "localhost";
	private static final int MV_PORT = 1234;
	
	private String name;
	private RobotMessageControl msgCtrl;
	private VehicleModel v;
	private MVClient mv;
	public boolean mvOn = true;
	private final AutonomousControl ac;
	
	public RobotClient(String host, int port, String robot, String mvHost) {
		super(host, port);
		name = robot;
		v = new VehicleModel();
		try {
	        mv = new MVClient(mvHost, MV_PORT);
        } catch (UnknownHostException e1) {
        	System.err.println("ERROR: Could not find MV server.");
	        e1.printStackTrace();
	        System.exit(1);
        } catch (IOException e1) {
        	System.err.println("ERROR: Could not connect to to MV server.");
	        e1.printStackTrace();
	        System.exit(2);
        }
		msgCtrl = new RobotMessageControl(v);
		ac = new AutonomousControl(v, mv);
		
		System.out.println("- Client establishing connection with " + host + ":" + port);
		try {
			openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("- Connection established");
		(new LoopingThread("robot updates", 0, 100) {
            protected void mainLoop() {
//            	v.updatePosition();
            	try {
            		sendToServer(MessageFactory.createVehicleUpdate(getName(), v));
        			sendToServer(MessageFactory.createMVUpdate(getName(), mv));
        			
        			if (v.isNotDeadman()) {
        				boolean success = ac.update();
        				if (!success) {
        					System.err.println("Autonomous control is broken.");
        				}
        			} else {
        				v.setLeftMotor(90);
        				v.setRightMotor(90);
        			}
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
					if (message.getValue() instanceof MVInstruction) {
						mvOn = message.get(MVInstruction.class).mvOn;
					} else {
						msgCtrl.handleMessage(message);
					}
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
	 * @param e the exception raised.
	 */
	@Override
	protected void connectionException(Exception e){
		System.out.println("problem with connection");
		e.printStackTrace();
	}

	/**
	 * Hook method called after a connection has been established.
	 */
	@Override
	protected void connectionEstablished(){
		System.out.println("- Connection established with " + getHost() + ":" + getPort());
	}

	public static void main(String[] args) {
		String mvHost = MV_HOST;
		if (args.length > 0) {
			mvHost = args[0];
		}
		new RobotClient("localhost", 5555, "Gudra", mvHost);
	}
}
