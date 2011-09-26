package network.message;

import java.io.Serializable;

public class Movement implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double z;
	private double x;
	private double y;
	
	public Movement(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX(){
		return x;
	}
	
	public double getZ(){
		return z;
	}
	
	public double getY(){
		return y;
	}

}
