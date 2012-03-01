/*
 * Project:     ARISE Serial Device Library
 * Authors:     Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 * File:        ca/ariselab/lib/serialdevices/SerialPortMgmt.java
 * Date:        Wed 2011-04-27
 * License:     GNU General Public License version 2
 */
 
package ca.ariselab.lib.serialdevices;
 
import gnu.io.CommPortIdentifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class to be able to do some *easy* (not necessarily simple) serial
 * port operations.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
public final class SerialPortMgmt {
	
	/** The time to wait when inspecting the serial ports for device IDs. */
	private static final int INSPECTION_WAIT_DELAY = 2000;
	
	/** Whether or not to wait more during the serial ports inspection. */
	static boolean waitMore = false;
	
	/** A map of serial ports to SerialDeviceIDs. */
	public static Map<CommPortIdentifier, SerialDeviceID> devices;
	
	/** Do not allow instanciation of this class. */
	private SerialPortMgmt() {}
	
	/**
	 * Get a list of the computer's serial ports.
	 * @return The list of serial ports.
	 */
	public static ArrayList<CommPortIdentifier> getSerialPorts() {
		ArrayList<CommPortIdentifier> ret = new ArrayList<CommPortIdentifier>();
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> ports
		  = CommPortIdentifier.getPortIdentifiers();
		while (ports.hasMoreElements()) {
			CommPortIdentifier port = ports.nextElement();
			if (port.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				ret.add(port);
			}
		}
		return ret;
	}

	/**
	 * List the available serial ports to STDOUT.
	 */
	public static void listSerialPorts() {
		System.out.print("Found serial ports: ");
		for (CommPortIdentifier port : getSerialPorts()) {
			System.out.print(port.getName() + ", ");
		}
		System.out.println();
	}
	
	/**
	 * Print a list of all the currently running threads and their stack trace
	 * to STDOUT.
	 * Note: Used internally for development/debugging purposes.
	 */
	@SuppressWarnings("unused")
    private static void dumpThreads() {
		Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
		for (Thread th : threads.keySet()) {
			StackTraceElement[] stes = threads.get(th);
			System.out.println(th.getName() + " // " + stes.length);
			for (StackTraceElement ste : stes) {
				System.out.println("\t\t" + ste);
			}
		}
	}
	
	/**
	 * Detect the IDs of connected serial devices and load them into the
	 * internal map. 
	 * @param debug Whether or not to output debug information.
	 */
	private static void loadMap(boolean debug) {
		ArrayList<SerialPortInspector> spis
		  = new ArrayList<SerialPortInspector>();
		if (debug) {
			listSerialPorts();
		}
		
		// Create a serial device identifier object for each available serial
		// port
		for (CommPortIdentifier port : getSerialPorts()) {
			if (!port.isCurrentlyOwned()) {
				spis.add(new SerialPortInspector(port, debug));
			}
		}
		
		// Wait a short amount of time to allow the inspectors to get the IDs
		if (debug) {
			System.out.println("Waiting...");
		}
		waitMore = true;
		while (waitMore) {
			waitMore = false;
			try {
				Thread.sleep(INSPECTION_WAIT_DELAY);
			} catch (InterruptedException e) {
			}
		}
		if (debug) {
			System.out.println("...done waiting, load map.");
		}
		
		// Create a new map and populate it with the results
		devices = new HashMap<CommPortIdentifier, SerialDeviceID>();
		for (SerialPortInspector spi : spis) {
			
			// Close any inspectors that haven't finished inspecting 
			spi.close();
			
			// Put the port and detected device ID into the map
			devices.put(spi.getPort(), spi.getDeviceID());
		}
	}
	
	/**
	 * Get the port identifier corresponding to the specified device ID. 
	 * @param devID The ID of the desired attached device.
	 * @return A port identifier of the desired serial port.
	 */
	public static CommPortIdentifier getPortByDeviceId(SerialDeviceID devID) {
		return getPortByDeviceId(devID, false);
	}
	
	/**
	 * Get the port identifier corresponding to the specified device ID. 
	 * @param devID The ID of the desired attached device.
	 * @param debug Whether to output debug information or not.
	 * @return A port identifier of the desired serial port.
	 */
	public static CommPortIdentifier getPortByDeviceId(SerialDeviceID devID,
	  boolean debug) {
		
		// If the map has not been populated yet, do it now
		if (devices == null) {
			if (debug) {
				System.out.println(
				  "Device map is empty, proceeding to populate it.");
			}
			loadMap(debug);
			if (debug) {
				System.out.println("Loading complete.");
				System.out.println();
			}
		} else {
			if (debug) {
				System.out.println("Device map already loaded.");
			}
		}
		
		// Debug output
		if (debug) {
			System.out.println("Looking for device with id " + devID);
		}
		
		// Loop through the map looking for the specified device ID
		for (Map.Entry<CommPortIdentifier, SerialDeviceID> device :
		  devices.entrySet()) {
			if (devID.equals(device.getValue())) {
				return device.getKey();
			}
		}
		
		// The desired port was not found, return null
		return null;
	}
	
	/**
	 * Test the inspection protocol and print the results.
	 * @param id The ID of the device to find.
	 */
	private static void test(int id) {
		CommPortIdentifier port
		  = getPortByDeviceId(new SerialDeviceID(id), true);
		if (port != null) {
			System.out.println("FOUND device with id " + id + " on port "
			  + port.getName());
		} else {
			System.out.println("\tFAIL: could not found device with id " + id);
		}
		System.out.println();
	}
	
	/**
	 * Run some tests on the library.
	 * @param args The command-line arguments, not used.
	 */
	public static void main(String[] args) {
		test(0x70);
		test(0x75);
		test(0x80);
		test(0x85);
//		dumpThreads();
	}
}

/**
 * An object that will inspect the specified serial port to try to determine the
 * ID of the attached serial device.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
class SerialPortInspector {
	
	/** The port to inspect. */
	private CommPortIdentifier port;
	
	/** The SerialModule object used to inspect the serial port. */
	SerialModule sm;
	
	/** The detected device ID. */
	private SerialDeviceID devID;
	
	/** Whether or not to output debug information. */
	private boolean debug;
	
	/**
	 * Create a serial port inspector for the specified serial port.
	 * @param port The serial port to inspect.
	 */
	public SerialPortInspector(CommPortIdentifier port) {
		this(port, false);
	}
	
	/**
	 * Create a serial port inspector for the specified serial port.
	 * @param port The serial port to inspect.
	 * @debug Whether or not to output debug information.
	 */
	public SerialPortInspector(final CommPortIdentifier port,
	  final boolean debug) {
		this.port = port;
		this.debug = debug;
		try {
			
			// Create a SerialModule object and use it to check for a device ID
			sm = new SerialModule(port.getName()) {
				
				/**
				 * Read updates from the serial device.
				 */
				protected void readUpdatesFromDevice() throws IOException {
					
					// Wait until there is enough information in the buffer to
					// be able to read the ID
					if (bytesAvailable() < 4) {
						return;
					}
					
					// Check for the first start-of-message byte
					if (readByte() != 0x55) {
						return;
					}
					
					// Check for the second start-of-message byte
					if (readByte() != 0xFF) {
						return;
					}
					
					// Check for the third start-of-message byte
					if (readByte() != 0xAA) {
						return;
					}
					
					// And now read the device's ID
					devID = new SerialDeviceID(readByte());
					if (debug) {
						System.out.print("Detected id " + devID + " on port "
						  + port.getName());
					}
					
					// Close the SerialModule connection
					close();
					if (debug) {
						System.out.println("... port " + port.getName()
						  + " closed.");
					}
					
					// Tell the SerialPortMgmt that a device ID has been
					// successfully detected and that it can spend a bit more
					// time waiting for other serial ports
					SerialPortMgmt.waitMore = true;
				}
				
				/**
				 * Send the updates to the serial device.
				 */
				protected void sendUpdatesToDevice() throws IOException {
					
					// Don't bother sending anything
					stopWriting();
				}
			};
			sm.begin();
		} catch (SerialDeviceInitException e) {
		}
	}
	
	/**
	 * Close the SerialModule if it hasn't already been done.
	 */
	public void close() {
		if (!sm.isClosed()) {
			if (debug) {
				System.out.print("Timeout for port " + port.getName());
			}
			sm.close();
			if (debug) {
				System.out.println("... port " + port.getName() + " closed.");
			}
		}
	}
	
	/**
	 * Get the detected device ID.
	 * @return The detected device ID of the attached serial device.
	 */
	public SerialDeviceID getDeviceID() {
		return devID;
	}
	
	/**
	 * Get the port identifier used by this inspector.
	 * @return The port identifier.
	 */
	public CommPortIdentifier getPort() {
		return port;
	}
}
