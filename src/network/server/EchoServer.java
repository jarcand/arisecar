package network.server;

import network.message.Position;

import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

public class EchoServer extends AbstractServer {
	//Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	//Constructors ****************************************************

	public EchoServer(){
		this(DEFAULT_PORT);
	}
	
	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public EchoServer(int port) {
		super(port);

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
	public void handleMessageFromClient(Object msg, ConnectionToClient client){
		sendToAllClients(msg);
		/*if(client.getInfo("nom_utilisateur") == null){
			client.setInfo("nom_utilisateur", msg);
			map.addCar((String)msg);
		}else{
			map.addMessage(new Message((String)client.getInfo("nom_utilisateur"), msg));
			//System.out.println("Message received: " + msg + " from " + client);
			//this.sendToAllClients((String)(client.getInfo("nom_utilisateur"))+ " : " + msg);
		}*/
	}
	
	/**
	 * Send the position of a car to all user.
	 */
	public void sendPosition(Position position){
		sendToAllClients(position);
	}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server starts listening for connections.
	 */
	protected void serverStarted(){
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server stops listening for connections.
	 */
	protected void serverStopped(){
		System.out.println("Server has stopped listening for connections.");
	}

	//Class methods ***************************************************

	/**
	 * This method is responsible for the creation of 
	 * the server instance (there is no UI in this phase).
	 *
	 * @param args[0] The port number to listen on.  Defaults to 5555 
	 *          if no argument is entered.
	 */
	public static void main(String[] args) {
		new EchoServer();
	}
}
