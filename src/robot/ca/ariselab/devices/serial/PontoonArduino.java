/**
 * Project:     ARISE AUV
 * Subproject:  Network ROV
 * Authors:     Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 * File:        devices/ArduinoServoDevice.java
 * Date:        Fri 2011-02-25
 */

package ca.ariselab.devices.serial;

import java.io.IOException;
import ca.ariselab.lib.serialdevices.SerialDeviceID;
import ca.ariselab.lib.serialdevices.SerialDeviceInitException;
import ca.ariselab.lib.serialdevices.SerialModule;
import ca.ariselab.lib.serialdevices.SerialPortMgmt;
import ca.ariselab.utils.ADC10;
import ca.ariselab.utils.BuffAvg;
//import ca.ariselab.utils.Format;


public abstract class PontoonArduino extends SerialModule {
	
	private String friendlyID;
	private int ardID;
	private int gpioIn, gpioOut, motorForeIn, motorForeOut, motorAftIn, motorAftOut;
	private int thermometer1, thermometer2, ammeter1, ammeter2, voltmeter1, voltmeter2, analog4, analog5;
	private BuffAvg thermometer1buff, thermometer2buff, ammeter1buff, ammeter2buff;
	
	// Constructors ========================================================
	
	/**
	 * Create a new Arduino Pontoon object to communicate with the Arduino on the specified serial port.
	 */
	public PontoonArduino(SerialDeviceID devID) throws SerialDeviceInitException {
		super(devID);
		begin(20, 200);
		friendlyID = devID.toString();
		ardID = devID.toByte();
		motorForeOut = 90;
		motorAftOut = 90;
		thermometer1buff = new BuffAvg(100);
		thermometer2buff = new BuffAvg(100);
		ammeter1buff = new BuffAvg(100);
		ammeter2buff = new BuffAvg(100);
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
		writeByte(gpioOut);
		writeByte(motorForeOut);
		writeByte(motorAftOut);
		writeBytes(new byte[25]);
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
		
		gpioIn = readByte();
		motorForeIn = readByte();
		motorAftIn = readByte();
		thermometer1 = readShort();
		thermometer2 = readShort();
		ammeter1 = readShort();
		ammeter2 = readShort();
		voltmeter1 = readShort();
		voltmeter2 = readShort();
		analog4 = readShort();
		analog5 = readShort();
		
		for (int i = 0; i < 9; i++) {
			char c = (char) readByte();
			if (c != '.') {
				System.err.println("Invalid char: " + (int) c);
			}
		}
		
		inputsUpdated();
	}
	
	/**
	 * Method to be able to process the data received from the Arduino.
	 */
	protected abstract void inputsUpdated();
	
	// Accessors ===========================================================
	
	/**
	 * Get the actual value of the GPIO.
	 * @return Each bit represents the state of an input or output.
	 * Note: The Arduino decides which are inputs and which are outputs.
	 */
	public int getGPIO() {return gpioIn;}
	
	/**
	 * Get the actual value of the forward motor.
	 * @return The servo position of the ESC controlling the motor. 
	 */
	public int getMotorFore() {return motorForeIn;}

	/**
	 * Get the actual value of the aft motor.
	 * @return The servo position of the ESC controlling the motor. 
	 */
	public int getMotorAft() {
		return motorAftIn;
	}

	/**
	 * Get the latest reading of thermometer 1.
	 * @return Raw ADC10 reading.
	 */
	public float getThermometer1() {
		float celcius = ADC10.convertMCP9700A(thermometer1);
		thermometer1buff.add(celcius);
		return thermometer1buff.get();
	}

	/**
	 * Get the latest reading of thermometer 2.
	 * @return Raw ADC10 reading.
	 */
	public float getThermometer2() {
		float celcius = ADC10.convertMCP9700A(thermometer2);
		thermometer2buff.add(celcius);
		return thermometer2buff.get();
	}

	/**
	 * Get the latest reading of ammeter 1.
	 * @return The reading in amps.
	 */
	public float getAmmeter1() {
		float amps = ADC10.convertASC756SCA100B(ammeter1);
		ammeter1buff.add(amps);
		return ammeter1buff.get();
	}

	/**
	 * Get the latest reading of ammeter 1.
	 * @return The reading in amps.
	 */
	public float getAmmeter2() {
		float amps = ADC10.convertASC756SCA100B(ammeter2);
		ammeter2buff.add(amps);
		return ammeter2buff.get();
	}

	/**
	 * Get the latest reading of voltmeter 1.
	 * @return The reading in volts.
	 */
	public float getVoltmeter1() {
		return (voltmeter1 * 5 / 1024f) * 3.2f;
	}

	/**
	 * Get the latest reading of voltmeter 2.
	 * @return The reading in volts.
	 */
	public float getVoltmeter2() {
		return (voltmeter2 * 5 / 1024f) * 3.2f;
	}

	/**
	 * Get the latest reading of analog input 4.
	 * @return Raw ADC10 reading.
	 */
	public int getAnalog4() {
		return analog4;
	}

	/**
	 * Get the latest reading of analog input 5.
	 * @return Raw ADC10 reading.
	 */
	public int getAnalog5() {
		return analog5;
	}

	/**
	 * Set the desired states for the outputs of the GPIO.
	 * @param gpio Each bit represents the state of output.
	 */
	public void setGPIO(int gpio) {
		this.gpioOut = gpio;
	}

	/**
	 * Set the desired value for the forward motor.
	 * @param motorFore The servo position of the ESC controlling the motor.
	 */
	public void setMotorFore(int motorFore) {
		this.motorForeOut = motorFore;
	}

	/**
	 * Set the desired value for the aft motor.
	 * @param motorAft The servo position of the ESC controlling the motor.
	 */
	public void setMotorAft(int motorAft) {
		this.motorAftOut = motorAft;
	}

	// Development / Debug =================================================

	public String toString() {
		return "PontoonArduino(" + friendlyID + "): " + getGPIO() + " " + getMotorFore() + " " + getMotorAft()
		  + " " + getThermometer1() + " " + getThermometer2() + " " + getAmmeter1() + " " + getAmmeter2()
		  + " " + getVoltmeter1() + " " + getVoltmeter2() + " " + getAnalog4() + " " + getAnalog5();
	}
	
	public static void main(String[] args) {
		SerialPortMgmt.listSerialPorts();
		try {
			new PontoonArduino(new SerialDeviceID(0x70)) {
				public void inputsUpdated() {
					System.out.println(toString());
				}
			};
		} catch (SerialDeviceInitException e) {
			e.printStackTrace();
		}
	}	
}

