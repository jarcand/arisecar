package networking;

public class XboxMovement {

	private double z;
	private double x;
	private double y;
	private double rx;
	
	public XboxMovement(double x, double y, double z, double rx){
		this.x = x;
		this.y = y;
		this.z = z;
		this.rx = rx;
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
	
	public double getRX(){
		return rx;
	}

}
