package server;

import robot.Robot;
import server.logging.GeneralLog;
import util.Log;

import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

public class HostServer extends AbstractServer{

	/**
	 * The default port to listen on.
	 */
	public static final int DEFAULT_PORT = 5555;
	
	private final GeneralLog log;

	//Constructors ****************************************************

	public HostServer(){
		this(DEFAULT_PORT);
	}

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public HostServer(int port) {
		super(port);
		log = new GeneralLog();
		//new ServerWindow();
		try {
			listen(); //Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
		
	}


	//Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	@Override
	public void handleMessageFromClient(Object msg, ConnectionToClient client){
		if(msg instanceof Message){
			Message message = (Message)msg;
			if(message.isFromRobot()){
				log.messageFromRobot(message.toString(), message.getRobotName());
			}else if(message.isFromClient()){
				log.messageFromClient(message.toString(), client.getInetAddress().toString());
			}
			sendToAllClients(msg);
		}
	}

	/**
	 * Called when the server starts listening for connections.
	 */
	@Override
	protected void serverStarted(){
		log.statusFromServer("Server started on port " + getPort());
	}

	/**
	 * Called when the server stops listening for connections.
	 */
	@Override
	protected void serverStopped(){
		log.statusFromServer("Server has stopped.");
	}
	
	@Override
	protected void clientConnected(ConnectionToClient client){
		log.statusFromClient("Client connected to server : " + client.getInetAddress(), client.getName());
	}
	
	@Override
	protected void clientDisconnected(ConnectionToClient client){
		log.statusFromClient("Client disconnected from server : " + client.getInetAddress(), client.getName());
	}

	//Main Method ***************************************************

	/**
	 * This method is responsible for the creation of the server instance.
	 */
	public static void main(String[] args) {
		new HostServer();
	}

}
