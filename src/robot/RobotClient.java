package robot;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import networking.MVInstruction;
import networking.Message;
import networking.MessageFactory;
import ca.ariselab.utils.LoopingThread;
import com.lloseng.ocsf.client.AbstractClient;

/**
 * The main program for the robot, including the networking client that connects
 * to the host server.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
public class RobotClient extends AbstractClient {
	
	/** The default host of the Machine Vision server. */
	private static final String MV_HOST = "localhost";
	
	/** The default port of the Machine Vision server. */
	private static final int MV_PORT = 1234;
	
	private String name;
	private RobotMessageControl msgCtrl;
	private VehicleModel v;
	private MVClient mv;
	public boolean mvOn = true;
	private final AutonomousControl ac;
	private boolean connectedToHost = false;
	
	public RobotClient(String robot, String hsHost, int hsPort, String mvHost, int mvPort) {
		super(hsHost, hsPort);
		name = robot;
		v = new VehicleModel();
		try {
	        mv = new MVClient(mvHost, mvPort);
	        Log.logInfo("Connected to MV server at " + mvHost + ":" + mvPort);
        } catch (UnknownHostException e1) {
        	Log.logError("Could not find MV server at " + mvHost + ":" + mvPort);
        } catch (IOException e1) {
        	Log.logError("Could not connect to to MV server at " + mvHost + ":" + mvPort);
        }
		msgCtrl = new RobotMessageControl(v);
		ac = new AutonomousControl(v, mv);
		
		try {
			openConnection();
			connectedToHost = true;
			Log.logInfo("Connected to HostServer at " + hsHost + ":" + hsPort);
		} catch (SocketException e) {
			Log.logError("Could not connect to HostServer at " + hsHost + ":" + hsPort);
		} catch (IOException e) {
			Log.logError("Could not connect to HostServer at " + hsHost + ":" + hsPort);
		}
		(new LoopingThread("robot updates", 0, 100) {
            protected void mainLoop() {
//            	v.updatePosition();
            	try {
            		sendToServer(MessageFactory.createVehicleUpdate(getName(), v));
            		if (mv != null) {
            			sendToServer(MessageFactory.createMVUpdate(getName(), mv));
            		}
        			
        			if (v.isNotDeadman()) {
        				boolean success = ac.update();
        				if (!success) {
        					Log.logError("Autonomous control is broken.");
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
	
	public void sendToServer(Object msg) throws IOException	{
		if (connectedToHost) {
			super.sendToServer(msg);
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
		new RobotClient("Gudra", "localhost", 5555, mvHost, MV_PORT);
	}
}
