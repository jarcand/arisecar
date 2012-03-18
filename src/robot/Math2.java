package robot;

public class Math2 {
	
	/**
	 * The angle of the vector (x, y). The angle is between 0 and 2*PI.
	 * @param x The x value of the vector.
	 * @param y The y value of the vector.
	 * @return The angle of the vector (x, y).
	 */
	public static double findAngle(double x, double y){
		double theta = 0;
		if(x == 0){
			if(y>0){
				theta = Math.PI/2;
			}else if(y < 0){
				theta = Math.PI*3/2;
			}
		}
		if(x > 0){
			theta = Math.atan(y/Math.abs(x));
		}
		if(x < 0){
			theta = Math.PI - Math.atan(y/Math.abs(x));
		}

		if(theta < 0){
			theta += Math.PI*2;
		}
		return theta;
	}

}
