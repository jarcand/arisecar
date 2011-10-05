package hal;

/*
 * Project:     Pronox Security Robot
 * Authors:     Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 * File:        HAL.java
 * Date:        Thu 2011-09-29
 * Copyright:   Copyright (c) 2011 by the authors.  All rights reserved.
 * Description: The interface for the Hardware Abstraction Layer.
 */

public interface HAL {
	
	/* --[ OUTPUTS ]--------------------------------------------------------- */ 
	
	/**
	 * Set the thrust level of the left motor. 
	 * @param thrustLevel The thrust level: -100 is 100% reverse and +100 is
	 *    100% forward. 
	 */
	public void setLeftMotor(int thrustLevel);
	
	/**
	 * Get the thrust level of the left motor. 
	 * @return The thrust level: -100 is 100% reverse and +100 is 100% forward. 
	 */
	public int getLeftMotor();
	
	/**
	 * Set the thrust level of the right motor. 
	 * @param thrustLevel The thrust level: -100 is 100% reverse and +100 is
	 *    100% forward. 
	 */
	public void setRightMotor(int thrustLevel);
	
	/**
	 * Get the thrust level of the right motor. 
	 * @return The thrust level: -100 is 100% reverse and +100 is 100% forward. 
	 */
	public int getRightMotor();
	
	/**
	 * Set whether or not the left brake is active.
	 * @param active True means the motor is locked, false means it's unlocked.
	 */
	public void setLeftBrake(boolean active);
	
	/**
	 * Get whether or not the left brake is active.
	 * @return True means the motor is locked, false means it's unlocked.
	 */
	public boolean getLeftBrake();
	
	/**
	 * Set whether or not the right brake is active.
	 * @param active True means the motor is locked, false means it's unlocked.
	 */
	public void setRightBrake(boolean active);
	
	/**
	 * Get whether or not the right brake is active.
	 * @return True means the motor is locked, false means it's unlocked.
	 */
	public boolean getRightBrake();
	
	/**
	 * Set whether or not the relay for the motors is active.
	 * @param active True means the relay is closed, false means it's open.
	 */
	public void setMotorsRelay(boolean active);
	
	/**
	 * Get whether or not the relay for the motors is active.
	 * @return True means the relay is closed, false means it's open.
	 */
	public boolean getMotorsRelay();
	
	
	/* --[ INPUTS ]---------------------------------------------------------- */ 
	
	/**
	 * Get the rotation rate of the left motor. 
	 * @return The rotation rate: ticks per second, negative is reverse. 
	 */
	public int getLeftRate();
	
	/**
	 * Get the rotation rate of the right motor. 
	 * @return The rotation rate: ticks per second, negative is reverse. 
	 */
	public int getRightRate();
	
	/**
	 * Get the quantity of current consumed by the left motor.
	 * @return The number of miliamps.
	 */
	public int getLeftMotorCurrent();
	
	/**
	 * Get the quantity of current consumed by the right motor.
	 * @return The number of miliamps.
	 */
	public int getRightMotorCurrent();
	
	/**
	 * Get the quantity of current supplied the first battery.
	 * @return The number of miliamps.
	 */
	public int getBattery1Current();
	
	/**
	 * Get the voltage charge of the first battery.
	 * @return The number of milivolts.
	 */
	public int getBattery1Voltage();
	
	/**
	 * Get the quantity of current supplied the second battery.
	 * @return The number of miliamps.
	 */
	public int getBattery2Current();
	
	/**
	 * Get the voltage charge of the second battery.
	 * @return The number of milivolts.
	 */
	public int getBattery2Voltage();
	
	/**
	 * Get whether or not the e-stop is active.
	 * @return True means the system IS emergency-stopped.
	 */
	public boolean getEstop();
	
	/**
	 * Get the measured range for the specified sensor.
	 * @param index The number of the sensor.
	 * @return The range of the sensor in centimetres.
	 */
	public int getRange(int index);
	
	/**
	 * Get whether or not the specified sensor is measuring contact.
	 * @param index The number of the sensor.
	 * @return True means the sensor is contacted.
	 */
	public boolean getContact(int index);
}

