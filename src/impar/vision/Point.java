package impar.vision;

/**
 * A point is something that is gathered by the sonde.
 * 
 * @author Marc-Andre
 * 
 */
public class Point {
	
	/** The position where the point was gathered */
	public int x;
	/** The position where the point was gathered */
	public int y;
	/** The distance of the point */
	public double dist;
	/** The angle of the point */
	public double angle;
	/** How much we think that this point is valid */
	public int validity = 1; //Indicate the degree of validity of that point. 1 is the lowest value;
	/** 
	 * The time at which this point is registered.
	 */
	public long time = -1;
	
	public Point(int x, int y, double dist, double angle){
		this(x, y, dist, angle, 1);
	}
	
	public Point(int x, int y, double dist, double angle, int validity){
		this.x = x;
		this.y = y;
		this.dist = dist;
		this.angle = angle;
		this.validity = validity;
		while(this.angle > 2*Math.PI){
			this.angle -= 2*Math.PI;
		}
		while(this.angle < 0){
			this.angle += 2*Math.PI;
		}
	}
	
	public int x(){
		return (int) (x + dist*Math.cos(angle));
	}
	
	public int y(){
		return (int) (y + dist*Math.sin(angle));
	}

}
