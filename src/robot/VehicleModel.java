package robot;

import ca.ariselab.lib.serialdevices.SerialDeviceID;
import ca.ariselab.lib.serialdevices.SerialDeviceInitException;
import ca.ariselab.utils.Tools;

/**
 * An object with all the known properties of the vehicle.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
public class VehicleModel {
	
	// The arduino module is uses to control locomotion
	private LocoArduino locoArduino;
	
	// Some constant properties
	private final int RADIUS = 25;
	
	// The last know values of some properties
	private double heading = 0;
	private double posX = 0;
	private double posY = 0;
	
	// Parameters from which others are derived
	private float speed = 0;
	private float yawRate = 0;
	
	// The last time the update method was called
	private long lastUpdateTime = 0;
	
	/**
	 * Create a new vehicle model, include connecting to the loco arduino.
	 */
	public VehicleModel(){
		
		// Connect to the loco arduino
		SerialDeviceID devId = new SerialDeviceID(0x70);
		try {
			locoArduino = new LocoArduino(devId) {
				protected void inputsUpdated() {
					updatePosition();
				}
			};
			Log.logInfo("Connected to LocoArduino(" + devId + ") on port " + locoArduino.getPort());
		} catch (SerialDeviceInitException e) {
			Log.logFatal("Could not connect to LocoArduino(" + devId + ").");
		}
	}
	
	/**
	 * @return The last known X position.
	 */
	public double getPosX(){
		return posX;
	}
	
	/**
	 * @return The last known Y position.
	 */
	public double getPosY(){
		return posY;
	}
	
	/**
	 * @return The radius of the vehicle.
	 */
	public double getRadius(){
		return RADIUS;
	}
	
	/**
	 * @return The orientation of the vehicle.
	 */
	public double getHeading(){
		return heading;
	}
	
	/**
	 * @return Whether or not the deadman switch is being activate.
	 */
	public boolean isNotDeadman() {
		return 500 < locoArduino.getDeadman()
		  && locoArduino.getDeadman() < 1500;
	}
	
	/**
	 * Set the speed of the left motor.
	 * @param leftMotor The speed as a servo value.
	 */
	public void setLeftMotor(int leftMotor) {
		locoArduino.setMotorL(leftMotor);
	}
	
	/**
	 * Set the speed of the right motor.
	 * @param rightMotor The speed as a servo value.
	 */
	public void setRightMotor(int rightMotor) {
		locoArduino.setMotorR(rightMotor);
	}
	
	/**
	 * Set the speed of the vehicle.
	 * @param speed The speed, +1 full ahead, -1 full reverse.
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
		recalcMix();
	}
	
	/**
	 * Set the yaw rate of the vehicle.
	 * @param yawRate The yaw rate, +1 full right, -1 full left.
	 */
	public void setYawRate(float yawRate) {
		this.yawRate = yawRate;
		recalcMix();
	}
	
	/**
	 * Recalculate the speed/yaw mix to give the left/right motor speeds.
	 */
	private void recalcMix() {
		setLeftMotor(Tools.axisToPWM(Tools.mixSpeedYaw(speed, yawRate)));
		setRightMotor(Tools.axisToPWM(Tools.mixSpeedYaw(speed, -yawRate)));
	}
	
	/**
	 * Update the internal recorded position of the vehicle.
	 */
	private void updatePosition() {
		
		// Calculate the time since the last update
		long currentUpdateTime = System.nanoTime();
		if (lastUpdateTime == 0) {
			lastUpdateTime = currentUpdateTime;
			return;
		}
		long deltaTime = (currentUpdateTime - lastUpdateTime) / 1000 / 1000;
		lastUpdateTime = currentUpdateTime;
		
		// Get the actual motor setpoints
		// TODO: replace this with shaft encoder readings
		int motor1SetPoint = locoArduino.getMotorLSetPoint();
		if (motor1SetPoint < 1000) {
			motor1SetPoint = 1500;
		}
		int motor2SetPoint = locoArduino.getMotorRSetPoint();
		if (motor2SetPoint < 1000) {
			motor2SetPoint = 1500;
		}
		
		// Calculate the speed of each wheel
		double m1v = (motor1SetPoint - 1500) / 450.0 * 92 / 60 * 102.8;
		double m2v = (motor2SetPoint - 1500) / 450.0 * 97 / 60 * 102.6;
		
		// Calculate the resulting distance and rotation traveled
		double dist = (m1v + m2v) / 2.0 / 1000 * deltaTime; 
		double rot = (m1v - m2v) / 2.0 * (2 * Math.PI) / 171.2 / 1000 * deltaTime;
		
		// Update the position and the heading
		posX += Math.cos(heading) * dist;
		posY += Math.sin(heading) * dist;
		heading += rot;
	}
	
	/**
	 * A stand-alone test of this class. 
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		VehicleModel v = new VehicleModel();
		v.setYawRate(1);
	}
}
