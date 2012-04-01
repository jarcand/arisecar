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


public abstract class LocoArduino extends SerialModule {
	
	private class Inputs {
		int motor1Target, motor2Target, motor1SetPoint, motor2SetPoint;
		int rangeFinder1, rangeFinder2, rangeFinder3, deadman;
	}
	private String friendlyID;
	private int ardID;
	private int motor1Out = 90, motor2Out = 90;
	private Inputs inputs = new Inputs();
	
	// Constructors ========================================================
	
	/**
	 * Create a new Arduino Pontoon object to communicate with the Arduino on the specified serial port.
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
		writeByte(motor1Out);
		writeByte(motor2Out);
		writeBytes(new byte[26]);
	}
	
	/**
	 * Read the data from the Arduino.
	 */
	protected synchronized void readUpdatesFromDevice() throws IOException {
		if (readByte() != 0x55) {
			System.out.print('b');
			return;
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
		
		Inputs newData = new Inputs();
		newData.motor1Target = readByte();
		newData.motor2Target = readByte();
		newData.motor1SetPoint = readShort();
		newData.motor2SetPoint = readShort();
		newData.rangeFinder1 = readShort();
		newData.rangeFinder2 = readShort();
		newData.rangeFinder3 = readShort();
		newData.deadman = readShort();
		
		boolean err = false;
		for (int i = 0; i < 14; i++) {
			char c = (char) readByte();
			if (c != '.') {
				System.err.println("Invalid char: " + (int) c);
				err = true;
			}
		}
		if (!err) {
			inputs = newData;
		}
		
		inputsUpdated();
	}
	
	/**
	 * Method to be able to process the data received from the Arduino.
	 */
	protected abstract void inputsUpdated();
	
	// Accessors ===========================================================
	
	/**
	 * Get the desired value for motor 1.
	 * @return The desired servo position of the ESC controlling the motor.
	 */
	public int getMotor1Target() {
		return inputs.motor1Target;
	}

	/**
	 * Get the desired value for motor 2.
	 * @return The desired servo position of the ESC controlling the motor.
	 */
	public int getMotor2Target() {
		return inputs.motor2Target;
	}

	/**
	 * Get the actual value for motor 1.
	 * @return The actual pulse length of the ESC controlling the motor.
	 */
	public int getMotor1SetPoint() {
		return inputs.motor1SetPoint;
	}

	/**
	 * Get the actual value for motor 2.
	 * @return The actual pulse length of the ESC controlling the motor.
	 */
	public int getMotor2SetPoint() {
		return inputs.motor2SetPoint;
	}

	/**
	 * Get the latest reading of thermometer 1.
	 * @return Raw ADC10 reading.
	 *
	public float getThermometer1() {
		float celcius = ADC10.convertMCP9700A(motor1Target);
		thermometer1buff.add(celcius);
		return thermometer1buff.get();
	}*/

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
	 * Set the desired value for motor 1.
	 * @param motor1 The servo position of the ESC controlling the motor.
	 */
	public void setMotor1(int motor1) {
		motor1Out = motor1;
	}

	/**
	 * Set the desired value for motor 2.
	 * @param motor2 The servo position of the ESC controlling the motor.
	 */
	public void setMotor2(int motor2) {
		motor2Out = motor2;
	}

	// Development / Debug =================================================

	public String toString() {
		return "LocoArduino(" + friendlyID + "): "
		  + getMotor1Target() + " " + getMotor2Target() + " "
		  + getMotor1SetPoint() + " " + getMotor2SetPoint() + " "
		  + getRangeFinder1() + " " + getRangeFinder2() + " "
		  + getRangeFinder3() + " " + getDeadman();
	}
	
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

