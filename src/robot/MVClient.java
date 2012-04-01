package robot;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import ca.ariselab.utils.LoopingThread;

/**
 * An object that contains the client connection to the Machine Vision server.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
public class MVClient  {
	
	// The default port to connect to
	public static int DEFAULT_PORT = 1234;
	
	// The request rate and period for the communications
	public static int REQ_RATE = 20;
	public static int REQ_PERIOD = 1000 / REQ_RATE;
	
	// Objects for the network connection 
	private Socket clientSocket;
	private OutputStream outToServer;
	private InputStream inFromServer; 
	
	// The most recent response
	private int response = 0;
	
	/**
	 * Create a new client to the MV server specified.
	 * @param host The hostname of the server to connect to.
	 * @throws UnknownHostException If the hostname is not resolvable.
	 * @throws IOException If there is a problem making the connection.
	 */
	public MVClient(String host) throws UnknownHostException, IOException {
		this(host, DEFAULT_PORT);
	}
	/**
	 * Create a new client to the MV server specified.
	 * @param host The hostname of the server to connect to.
	 * @param port The port number of the server.
	 * @throws UnknownHostException If the hostname is not resolvable.
	 * @throws IOException If there is a problem making the connection.
	 */
	public MVClient(String host, int port) throws UnknownHostException, IOException {
		
		// Connect to the server and get the communcation streams
		clientSocket = new Socket(host, port);
		outToServer = clientSocket.getOutputStream();
		inFromServer = clientSocket.getInputStream();
		
		// Create a new thread to do the communications
		(new LoopingThread("MV client", 0, REQ_PERIOD) {
			public void mainLoop() {
				try {
					
					// Make a request to the server
					outToServer.write('a');
					
					// Read it's reponse
					// TODO: NOTE: this assumes the connection remains synchronized, which might not be the case 
					response = inFromServer.read();
					
					// Notify any listeners
					responseUpdated();
					
				// If the socket has a problem, close the connection and end the thread
				} catch (SocketException e) {
					System.err.println("Client connection to MV server has died.");
					try {
	                    outToServer.close();
                    } catch (IOException e1) {
	                    System.err.println("Error closing the output stream.");
                    }
					try {
	                    inFromServer.close();
                    } catch (IOException e1) {
	                    System.err.println("Error closing the input stream.");
                    }
					try {
	                    clientSocket.close();
                    } catch (IOException e1) {
	                    System.err.println("Error closing the socket.");
                    }
					stopLoopThread();
				
				// If there's another type of IO exception, print the information
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	/** A method called when the MV response has been updated */
	public void responseUpdated() {}
	
	/**
	 * Send a calibration signal/message to the server. 
	 * @return Whether it is successfull or not.
	 */
	public boolean calibrate() {
		boolean ret = false;
		try {
	        outToServer.write('c');
	        ret = true;
        } catch (IOException e) {
	        e.printStackTrace();
        }
        return ret;
	}
	
	/**
	 * Get the latest response from the server.
	 * @return The response, which is a 4-bit bitfield with each bits representing whether RIGHT, LEFT, UP, and DOWN zones are clear.
	 */
	public int getResponse() {
		return response;
	}
	
	/**
	 * @return Whether or not the DOWN zone is clear.
	 */
	public boolean isDownZoneClear() {
		return (response & 0x1) == 0x1;
	}
	
	/**
	 * @return Whether or not the UP zone is clear.
	 */
	public boolean isUpZoneClear() {
		return (response & 0x2) == 0x2;
	}
	
	/**
	 * @return Whether or not the LEFT zone is clear.
	 */
	public boolean isLeftZoneClear() {
		return (response & 0x4) == 0x4;
	}
	
	/**
	 * @return Whether or not the RIGHT zone is clear.
	 */
	public boolean isRightZoneClear() {
		return (response & 0x8) == 0x8;
	}
	
	/**
	 * Return a string representation of the current response.
	 */
	public String toString() {
		return "down: " + isDownZoneClear() + ", up: " + isUpZoneClear() + ", left: "
		  + isLeftZoneClear() + ", right: " + isRightZoneClear();
	}
	
	/**
	 * A test for the MV client.
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		// Start the client
		final MVClient mv = new MVClient("192.168.3.110", 1234);
		
		// Output the response on a regular interval
		(new LoopingThread("MV test", 0, REQ_PERIOD * 10) {
			public void mainLoop() {
				System.out.println("response: " + mv.toString());
			}
		}).start();
		
		// Make a calibration request on another longer interval
		(new LoopingThread("MV calibrate", 2000, 2000) {
			protected void mainLoop() {
				System.out.println("calibrate: " + mv.calibrate());
			}
		}).start();
	}
}
