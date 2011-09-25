package network.message;

import java.io.Serializable;

public class Position implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String name;
	private final double x;
	private final double y;
	private final double angle;
	private final int motorRight;
	private final int motorLeft;
	
	public Position(String name, double x, double y, double angle, int motorRight, int motorLeft){
		this.name = name;
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.motorRight = motorRight;
		this.motorLeft = motorLeft;
	}
	
	public String getName(){
		return name;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getAngle(){
		return angle;
	}
	
	public int getMotorRight(){
		return motorRight;
	}
	
	public int getMotorLeft(){
		return motorLeft;
	}

}
