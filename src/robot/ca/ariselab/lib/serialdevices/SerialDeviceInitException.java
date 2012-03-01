/*
 * Project:     ARISE Serial Device Library
 * Authors:     Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 * File:        ca/ariselab/lib/serialdevices/SerialDeviceInitException.java
 * Date:        Wed 2011-04-27
 * License:     GNU General Public License version 2
 */
 
package ca.ariselab.lib.serialdevices;

/**
 * An exception that is thrown when there is a problem initializing a serial
 * device.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
public class SerialDeviceInitException extends Exception {
	
	/** A unique serialVersionUID for serialization. */
	private static final long serialVersionUID = -7197927645221536971L;

	/**
	 * Construct a new exception with the provided error message and original
	 * expcetion.
	 * @param ex The original exception that is the cause of the error.
	 * @param msg A message describing the error.
	 */
	public SerialDeviceInitException(Exception ex, String msg) {
		super(msg, ex);
	}
	
	/**
	 * Construct a new exception with the provided error message.
	 * @param msg A message describing the error.
	 */
	public SerialDeviceInitException(String msg) {
		super(msg);
	}
}

