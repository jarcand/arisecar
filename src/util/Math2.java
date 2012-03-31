package util;

public class Math2 {

	public static double findAngle(double x, double y)
	{ 
		double theta = 0;           
		if(x == 0)
		{                 
			if(y>0)
			{                        
				theta = Math.PI/2;                
			}else if(y < 0)
			{                     
				theta = Math.PI*3/2;           
			}   
			
		}        
		if(x > 0)
		{                 
			theta = Math.atan(y/Math.abs(x));    
		}           
		if(x < 0)
		{           
			theta = Math.PI - Math.atan(y/Math.abs(x));       
		}       
		if(theta < 0)
		{            
			theta += Math.PI*2;       
		}    
		
		return theta;   
	}  
}
