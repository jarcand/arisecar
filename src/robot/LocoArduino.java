/**
 * Project:     ARISE AUV
 * Subproject:  Network ROV
 * Authors:     Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 * File:        devices/ArduinoServoDevice.java
 * Date:        Fri 2011-02-25
 */

package robot;

import java.io.IOException;
import ca.ariselab.lib.serialdevices.SerialDeviceID;
import ca.ariselab.lib.serialdevices.SerialDeviceInitException;
import ca.ariselab.lib.serialdevices.SerialModule;
import ca.ariselab.lib.serialdevices.SerialPortMgmt;

/**
 * A class that represents the state of the arduino that has the locomotion
 * circuits attached.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
public abstract class LocoArduino extends SerialModule {
	
	/** A struct to of all the inputs from the arduino. */
	private class Inputs {
		int motorLTarget, motorRTarget, motorLSetPoint, motorRSetPoint;
		int rangeFinder1, rangeFinder2, rangeFinder3, deadman;
	}
	
	// Properties of the module
	private String friendlyID;
	private int ardID;
	
	// I/O state variables 
	private int motorLOut = 90, motorROut = 90;
	private Inputs inputs = new Inputs();
	
	// Constructors ========================================================
	
	/**
	 * Create a new Arduino Pontoon object to communicate with the Arduino on
	 * the specified serial port.
	 */
	public LocoArduino(SerialDeviceID devID) throws SerialDeviceInitException {
		super(devID);
		begin(20, 200);
		friendlyID = devID.toString();
		ardID = devID.toByte();
	}
	
	// Arduino communication ===============================================
	
	/**
	 * Send the data to the Arduino.
	 */
	protected synchronized void sendUpdatesToDevice() throws IOException {
		
		// Write the outputs to the serial port
		writeByte(0x55); // start-of-message-byte-1
		writeByte(0xFF); // start-of-message-byte-2
		writeByte(0xAB); // start-of-message-byte-3
		writeByte(ardID); // start-of-message-byte-4
		writeByte(motorLOut);
		writeByte(motorROut);
		writeBytes(new byte[26]);
	}
	
	/**
	 * Read the data from the Arduino.
	 */
	protected synchronized void readUpdatesFromDevice() throws IOException {
		
		// Make sure the message is synchronized 
		while (readByte() != 0x55) {
			System.out.print('b');
		}
		if (readByte() != 0xFF) {
			System.out.print('c');
			return;
		}
		if (readByte() != 0xAA) {
			System.out.print('d');
			return;
		}
		if (readByte() != ardID) {
			System.out.print('e');
			return;
		}
		
		// Get all the inputs from the arduino and store them temporarily
		Inputs tempInputs = new Inputs();
		tempInputs.motorLTarget = readByte();
		tempInputs.motorRTarget = readByte();
		tempInputs.motorLSetPoint = readShort();
		tempInputs.motorRSetPoint = readShort();
		tempInputs.rangeFinder1 = readShort();
		tempInputs.rangeFinder2 = readShort();
		tempInputs.rangeFinder3 = readShort();
		tempInputs.deadman = readShort();
		
		// Ensure the rest of the message is blank as expected
		boolean err = false;
		for (int i = 0; i < 14; i++) {
			char c = (char) readByte();
			if (c != '.') {
				System.err.println("Invalid char: " + (int) c);
				err = true;
			}
		}
		
		// If there were no errors, approve the temporary input values,
		// and notify the subclass that the inputs have been updated
		if (!err) {
			inputs = tempInputs;
			inputsUpdated();
		}
	}
	
	/**
	 * Method to be able to process the data received from the Arduino.
	 */
	protected abstract void inputsUpdated();
	
	// Accessors ===========================================================
	
	/**
	 * Get the desired value for the left motor.
	 * @return The desired servo position of the ESC controlling the motor.
	 */
	public int getMotorLTarget() {
		return inputs.motorLTarget;
	}

	/**
	 * Get the desired value for the right motor.
	 * @return The desired servo position of the ESC controlling the motor.
	 */
	public int getMotorRTarget() {
		return inputs.motorRTarget;
	}

	/**
	 * Get the actual value for the left motor.
	 * @return The actual pulse length of the ESC controlling the motor.
	 */
	public int getMotorLSetPoint() {
		return inputs.motorLSetPoint;
	}

	/**
	 * Get the actual value for the right motor.
	 * @return The actual pulse length of the ESC controlling the motor.
	 */
	public int getMotorRSetPoint() {
		return inputs.motorRSetPoint;
	}

	/**
	 * Get the latest reading of range finder 1.
	 * It's the front left one (+30 degree)
	 * @return The reading in centimetres.
	 */
	public float getRangeFinder1() {
		return inputs.rangeFinder1;
	}

	/**
	 * Get the latest reading of range finder 2.
	 * It's the front one (+0 degree)
	 * @return The reading in centimetres.
	 */
	public float getRangeFinder2() {
		return inputs.rangeFinder2;
	}

	/**
	 * Get the latest reading of range finder 3.
	 * It's the front right one (-30 degree)
	 * @return The reading in centimetres.
	 */
	public float getRangeFinder3() {
		return inputs.rangeFinder3;
	}
	
	/**
	 * Get the latest reading of the deadman switch.
	 * @return The reading as a RC PWM duty cycle.
	 */
	public int getDeadman() {
		return inputs.deadman;
	}

	/**
	 * Set the desired value for the left motor.
	 * @param leftMotor The servo position of the ESC controlling the motor.
	 */
	public void setMotorL(int leftMotor) {
		motorLOut = (int) (1.1 * (leftMotor - 90)) + 90;
	}

	/**
	 * Set the desired value for the right motor.
	 * @param rightMotor The servo position of the ESC controlling the motor.
	 */
	public void setMotorR(int rightMotor) {
		motorROut = rightMotor;
	}

	// Development / Debug =================================================

	/**
	 * A text representation of all the inputs/outputs.
	 */
	public String toString() {
		return "LocoArduino(" + friendlyID + "): "
		  + getMotorLTarget() + " " + getMotorRTarget() + " "
		  + getMotorLSetPoint() + " " + getMotorRSetPoint() + " "
		  + getRangeFinder1() + " " + getRangeFinder2() + " "
		  + getRangeFinder3() + " " + getDeadman();
	}
	
	/**
	 * A stand-alone test of the class.
	 * @param args
	 */
	public static void main(String[] args) {
		SerialPortMgmt.listSerialPorts();
		try {
			new LocoArduino(new SerialDeviceID(0x70)) {
				public void inputsUpdated() {
					System.out.println(toString());
				}
			};
		} catch (SerialDeviceInitException e) {
			e.printStackTrace();
		}
	}	
}

