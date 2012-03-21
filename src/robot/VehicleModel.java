package robot;

import ca.ariselab.lib.serialdevices.SerialDeviceID;
import ca.ariselab.lib.serialdevices.SerialDeviceInitException;

/**
 * Represent the vision the car has of itself. This class describe where the car
 * think he is, what angle he think he is facing, etc.
 * 
 * The function of this class are not complete for now but they might become at
 * some point depending how far we get this simulation to.
 * 
 * Currently this class is mostly used to draw the representation of the car on
 * the PointMap.
 * 
 * @author Gudradain
 *
 */
public class VehicleModel {
	
	public LocoArduino locoArduino;
	
	private double angle = 0;
	private double radius = 25;
	private double posX = 0;
	private double posY = 0;
	
	private long lastUpdateTime = 0;
	
	public VehicleModel(){
		try {
			locoArduino = new LocoArduino(new SerialDeviceID(0x70)) {
	        	private int i = 0;
	            protected void inputsUpdated() {
	            	i++;
	            	if (i % 20 == 0) {
//	            		System.out.print(".");
	            	}
	            }
			};
		} catch (SerialDeviceInitException e) {
			e.printStackTrace();
		}
	}
	
	public double getPosX(){
		return posX;
	}
	
	public double getPosY(){
		return posY;
	}
	
	public double getRadius(){
		return radius;
	}
	
	public double getAngle(){
		return angle;
	}
	
	public void setMotor1(int motor1) {
		locoArduino.setMotor1(motor1);
	}
	
	public void setMotor2(int motor2) {
		locoArduino.setMotor2(motor2);
	}
	
	public void update() {
		long currentUpdateTime = System.nanoTime();
		if (lastUpdateTime == 0) {
			lastUpdateTime = currentUpdateTime;
			return;
		}
		long deltaTime = (currentUpdateTime - lastUpdateTime) / 1000 / 1000;
		lastUpdateTime = currentUpdateTime;
 
		int motor1SetPoint = locoArduino.getMotor1SetPoint();
		if (motor1SetPoint < 1000) {
			motor1SetPoint = 1500;
		}
		int motor2SetPoint = locoArduino.getMotor2SetPoint();
		if (motor2SetPoint < 1000) {
			motor2SetPoint = 1500;
		}
		double m1v = (motor1SetPoint - 1500) / 450.0 * 92 / 60 * 102.8;
		double m2v = (motor2SetPoint - 1500) / 450.0 * 97 / 60 * 102.6;
		double dist = (m1v + m2v) / 2.0 / 1000 * deltaTime; 
		double rot = (m1v - m2v) / 2.0 * (2 * Math.PI) / 171.2 / 1000 * deltaTime;
		
		angle += rot;
		posX += Math.cos(angle) * dist;
		posY += Math.sin(angle) * dist;
	}

}
