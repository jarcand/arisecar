/*
 * Project:     ARISE Serial Device Library
 * Authors:     Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 * File:        ca/ariselab/lib/serialdevices/SerialDeviceID.java
 * Date:        Wed 2011-04-27
 * License:     GNU General Public License version 2
 */

package ca.ariselab.lib.serialdevices;

/**
 * A wrapper class to take advantage of strong variable typing to force the
 * programmer to demonstrate that they know what they want to do.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
public class SerialDeviceID {
	
	/** The identifying integer. */
	private int id;
	
	/**
	 * Construct a new SerialDeviceID object using the provided integer value.
	 * @param id The desired value of the SerialDeviceID.
	 */
	public SerialDeviceID(int id) {
		this.id = id;
	}
	
	/**
	 * Return the ID as a byte to be sent over serial.
	 * @return The ID of the device.
	 */
	public int toByte() {
		return id;
	}
	
	/**
	 * Return a user-friendly reprensentation of this object.
	 */
	public String toString() {
		return "SerialDeviceID(" + id + ")";
	}
	
	/**
	 * Determine if the provided SerialDeviceId is equal to this instance.
	 * @param otherID The other SerialDeviceId to compare this object to.
	 * @return Whether or not the object is equal to the current one. 
	 */
	public boolean equals(SerialDeviceID otherID) {
		return otherID == null ? false : otherID.id == id;
	}
}
