package simulation.robot.module;

import network.message.Movement;
import simulation.robot.AriseCar;

public class RobotMovement {
	
	private final AriseCar robot;
	private final RobotData data;
	
	public RobotMovement(AriseCar robot, RobotData data){
		this.robot = robot;
		this.data = data;
	}
	
	public void handleMessage(Object obj){
		if(obj instanceof Movement){
			Movement movement = (Movement)obj;
			double speed = 0.060*movement.getZ();
			double x = movement.getX();
			double y = movement.getY();
			double d = Math.sqrt(x*x+y*y);
			double angle;
			if(d > 0.8){
				angle = findAngle(y, x);
			}else{
				angle = 0;
			}
			
			double value;
			if(angle >= 0 && angle <= Math.PI){
				if(angle <= Math.PI/2){
					value = angle/(Math.PI/2);
				}else{
					value = (Math.PI - angle)/(Math.PI/2);
				}
				data.rotSpeedLeft = speed;
				data.rotSpeedRight = speed*(1-value*0.5);
			}else{
				if(angle < 3*Math.PI/2){
					value = (angle - Math.PI)/(Math.PI/2);
				}else{
					value = (2*Math.PI - angle)/(Math.PI/2);
				}
				data.rotSpeedLeft = speed*(1-value*0.5);
				data.rotSpeedRight = speed;
			}
			
			
			/*if(x > 0){
				double a = Math.acos(x);
				double f = 1-a/(Math.PI/2);
				data.rotSpeedLeft = speed;
				data.rotSpeedRight = speed*(1-x*0.5);
			}else{
				double a = Math.acos(-x);
				double f = 1-a/(Math.PI*2);
				data.rotSpeedRight = speed;
				data.rotSpeedLeft = speed*(1+x*0.5);
			}*/
			//data.rotSpeedLeft = speed*value;
			//data.rotSpeedRight = speed*(-value);
		}
	}
	
	/**
	 * The angle of the vector (x, y). The angle is between 0 and 2*PI.
	 * @param x The x value of the vector.
	 * @param y The y value of the vector.
	 * @return The angle of the vector (x, y).
	 */
	private double findAngle(double x, double y){
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
	
	public void updateMovement(int deltaTime){
		double speedRight = data.rotSpeedRight*data.circRight;
		double speedLeft = data.rotSpeedLeft*data.circLeft;
		if(speedRight == speedLeft){
			//Move in straight line
			double speed = speedRight*deltaTime;
			double dx = speed*Math.cos(data.angle);
			double dy = speed*Math.sin(data.angle);
			data.posX += dx;
			data.posY += dy;
		}else{
			//Find the center of the circle around which the car move
			double centerX;
			double centerY;
			double distRot;
			double speedRot = 0;
			
			if(speedRight*speedLeft < 0){
				//The center is between the 2 wheels
				double dist = Math.abs(speedRight)/(Math.abs(speedRight)+Math.abs(speedLeft))*data.distanceBetweenWheel;
				double newAngle = data.angle-Math.PI/2;
				centerX = data.posX + data.distanceBetweenWheel/2*Math.cos(newAngle) - dist*Math.cos(newAngle);
				centerY = data.posY + data.distanceBetweenWheel/2*Math.sin(newAngle) - dist*Math.sin(newAngle);
				if(Math.abs(speedRight) > Math.abs(speedLeft)){
					distRot = dist - (data.distanceBetweenWheel/2);
				}else{
					distRot = dist - (data.distanceBetweenWheel/2);
				}
				speedRot = distRot*speedRight/dist;
				
				double angleRot;
				if(distRot == 0){
					angleRot = (data.rotSpeedRight*data.circRight*deltaTime)/ (data.distanceBetweenWheel/2*2*Math.PI);
				}else{
					angleRot = speedRot*deltaTime / (distRot*2*Math.PI);
				}
				double dx = distRot*Math.cos(angleRot+data.angle-Math.PI/2);
				double dy = distRot*Math.sin(angleRot+data.angle-Math.PI/2);
				
				
				data.posX = centerX + dx;
				data.posY = centerY + dy;
				data.angle += angleRot;
			}else{
				//The center is outside of the 2 wheels
				if(Math.abs(speedRight) > Math.abs(speedLeft)){
					//The center is on the left side
					double k = speedLeft/speedRight;
					double dist = k*data.distanceBetweenWheel/(1-k);
					double newAngle = data.angle+Math.PI/2;
					centerX = data.posX + data.distanceBetweenWheel/2*Math.cos(newAngle) + dist*Math.cos(newAngle);
					centerY = data.posY + data.distanceBetweenWheel/2*Math.sin(newAngle) + dist*Math.sin(newAngle);
					distRot = dist + (data.distanceBetweenWheel/2);
					speedRot = speedLeft*distRot/dist;
					
					double angleRot = -speedRot*deltaTime / (distRot*2*Math.PI) * (Math.PI*2);
					double dx = distRot*Math.cos(-angleRot+data.angle-Math.PI/2);
					double dy = distRot*Math.sin(-angleRot+data.angle-Math.PI/2);
					
					data.posX = centerX + dx;
					data.posY = centerY + dy;
					data.angle += angleRot;
				}else{
					//The center is on the right side
					double k = speedRight/speedLeft;
					double dist = k*data.distanceBetweenWheel/(1-k);
					double newAngle = data.angle-Math.PI/2;
					centerX = data.posX + data.distanceBetweenWheel/2*Math.cos(newAngle) + dist*Math.cos(newAngle);
					centerY = data.posY + data.distanceBetweenWheel/2*Math.sin(newAngle) + dist*Math.sin(newAngle);
					distRot = -dist - (data.distanceBetweenWheel/2);
					speedRot = speedRight*distRot/dist;
					System.out.println(speedRot);
					
					double angleRot = speedRot*deltaTime / (distRot*2*Math.PI) * (Math.PI*2);
					double dx = distRot*Math.cos(-angleRot+data.angle-Math.PI/2);
					double dy = distRot*Math.sin(-angleRot+data.angle-Math.PI/2);
					
					data.posX = centerX + dx;
					data.posY = centerY + dy;
					data.angle += angleRot;
				}
			}
		}
	}

}
