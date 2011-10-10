package simulation.robot.module;

import simulation.robot.RobotSim;
import util.Log;

public class RobotMovement {
	
	private final RobotSim robot;
	private final RobotData data;
	
	public RobotMovement(RobotSim robot, RobotData data){
		this.robot = robot;
		this.data = data;
	}
	
	public void updateMovement(int deltaTime){ 
		double speedRight = data.rotSpeedRight*data.circRight;
		double speedLeft = data.rotSpeedLeft*data.circLeft;
		
		if(speedLeft == 0 && speedRight == 0){
			return;
			
		}else if(speedRight == speedLeft){
			//Move in straight line
			moveStraight(speedRight, speedLeft, deltaTime);
			
		}else if(speedLeft == 0 && speedRight != 0){
			//Rotate around left wheel
			rotateAroundLeft(speedRight, speedLeft, deltaTime);
			
		}else if(speedRight == 0 && speedLeft != 0){
			//Rotate around right wheel
			rotateAroundRight(speedRight, speedLeft, deltaTime);
		
		}else if(speedRight*speedLeft < 0 && Math.abs(speedLeft) == Math.abs(speedRight)){
			//Center inside middle
			rotateInsideMiddle(speedRight, speedLeft, deltaTime);
			
		}else if(speedRight*speedLeft < 0 && Math.abs(speedRight) > Math.abs(speedLeft)){
			//Center inside near left wheel
			rotateInsideLeft(speedRight, speedLeft, deltaTime);
			
		}else if(speedRight*speedLeft < 0 && Math.abs(speedLeft) > Math.abs(speedRight)){
			//Center inside near right wheel
			rotateInsideRight(speedRight, speedLeft, deltaTime);
			
		}else if(speedRight*speedLeft > 0 && Math.abs(speedRight) > Math.abs(speedLeft)){
			//Center outside near left wheel
			rotateOutsideLeft(speedRight, speedLeft, deltaTime);
			
		}else if(speedRight*speedLeft > 0 && Math.abs(speedLeft) > Math.abs(speedRight)){
			//Center outside near right wheel
			rotateOutsideRight(speedRight, speedLeft, deltaTime);
			
		}
	}

	private void rotateOutsideRight(double speedRight, double speedLeft, int deltaTime) {
		
		//The center is outside on the right side
		//This mean that speedLeft > speedRight in abs
		double centerX;
		double centerY;
		double distRot;
		double angleRot;
		double currentAngle;
		double reverseAngle;
		
		double factor = speedRight/speedLeft;
		double dist = factor*data.distanceBetweenWheel/(1-factor);
		distRot = dist + data.distanceBetweenWheel/2;
		
		currentAngle = data.angle + Math.PI/2;
		
		centerX = data.posX + distRot*Math.cos(currentAngle);
		centerY = data.posY + distRot*Math.sin(currentAngle);
		
		reverseAngle = currentAngle + Math.PI;
		angleRot = speedRight*deltaTime / (dist);
		double dx = distRot*Math.cos(reverseAngle + angleRot);
		double dy = distRot*Math.sin(reverseAngle + angleRot);
		
		data.posX = centerX + dx;
		data.posY = centerY + dy;
		data.angle += angleRot;
		
		Log.println(centerY);
		Log.debugMovement("Rotate outside right : " + centerX + " : " + distRot + " : " + angleRot);
	}

	private void rotateOutsideLeft(double speedRight, double speedLeft, int deltaTime) {
		//The center is outside on the left side
		//This mean that speedRight > speedLeft in abs
		double centerX;
		double centerY;
		double distRot;
		double angleRot;
		double currentAngle;
		double reverseAngle;
		
		double factor = speedLeft/speedRight;
		double dist = factor*data.distanceBetweenWheel/(1-factor);
		distRot = dist + data.distanceBetweenWheel/2;
		
		currentAngle = data.angle - Math.PI/2;
		centerX = data.posX + distRot*Math.cos(currentAngle);
		centerY = data.posY + distRot*Math.sin(currentAngle);
		
		reverseAngle = currentAngle + Math.PI;
		angleRot = -speedLeft*deltaTime / (dist);
		double dx = distRot*Math.cos(angleRot + reverseAngle);
		double dy = distRot*Math.sin(angleRot + reverseAngle);
		
		data.posX = centerX + dx;
		data.posY = centerY + dy;
		data.angle += angleRot;
		
		Log.debugMovement("Rotate outside left : " + centerX + " : " + distRot + " : " + angleRot);
	}
	
	private void rotateInsideMiddle(double speedRight, double speedLeft, int deltaTime) {
		Log.debugMovement("Rotate inside middle");
		//The center is between the 2 wheels near the right one.
		//This mean that speedLeft > speedRight in abs.
		double centerX;
		double centerY;
		double distRot;
		double angleRot;
		double currentAngle;
		double reverseAngle;
		
		double dist = data.distanceBetweenWheel/2;
		distRot = 0;
		
		currentAngle = data.angle-Math.PI/2;
		centerX = data.posX + distRot*Math.cos(currentAngle);
		centerY = data.posY + distRot*Math.sin(currentAngle);
		
		reverseAngle = currentAngle + Math.PI;
		angleRot = speedRight*deltaTime/(dist);
		double dx = distRot*Math.cos(angleRot + reverseAngle);
		double dy = distRot*Math.sin(angleRot + reverseAngle);
		
		data.posX = centerX + dx;
		data.posY = centerY + dy;
		data.angle += angleRot;
	}

	private void rotateInsideRight(double speedRight, double speedLeft, int deltaTime) {
		Log.debugMovement("Rotate inside right");
		//The center is between the 2 wheels near the right one.
		//This mean that speedLeft > speedRight in abs.
		double centerX;
		double centerY;
		double distRot;
		double angleRot;
		double currentAngle;
		double reverseAngle;
		
		double factor = Math.abs(speedRight)/(Math.abs(speedRight)+Math.abs(speedLeft));
		double dist = factor*data.distanceBetweenWheel;
		distRot = data.distanceBetweenWheel/2 - dist;
		
		currentAngle = data.angle-Math.PI/2;
		centerX = data.posX + distRot*Math.cos(currentAngle);
		centerY = data.posY + distRot*Math.sin(currentAngle);
		
		reverseAngle = currentAngle + Math.PI;
		angleRot = speedRight*deltaTime/(dist);
		double dx = distRot*Math.cos(angleRot + reverseAngle);
		double dy = distRot*Math.sin(angleRot + reverseAngle);
		
		data.posX = centerX + dx;
		data.posY = centerY + dy;
		data.angle += angleRot;
	}

	private void rotateInsideLeft(double speedRight, double speedLeft, int deltaTime) {
		Log.debugMovement("Rotate inside left");
		//The center is between the 2 wheels near the right one.
		//This mean that speedLeft > speedRight in abs.
		double centerX;
		double centerY;
		double distRot;
		double angleRot;
		double currentAngle;
		double reverseAngle;
		
		double factor = Math.abs(speedLeft)/(Math.abs(speedRight)+Math.abs(speedLeft));
		double dist = factor*data.distanceBetweenWheel;
		distRot = data.distanceBetweenWheel/2 - dist;
		
		currentAngle = data.angle+Math.PI/2;
		centerX = data.posX + distRot*Math.cos(currentAngle);
		centerY = data.posY + distRot*Math.sin(currentAngle);
		
		reverseAngle = currentAngle + Math.PI;
		angleRot = speedRight*deltaTime/(dist);
		double dx = distRot*Math.cos(angleRot + reverseAngle);
		double dy = distRot*Math.sin(angleRot + reverseAngle);
		
		data.posX = centerX + dx;
		data.posY = centerY + dy;
		data.angle += angleRot;
	}

	private void rotateAroundRight(double speedRight, double speedLeft, int deltaTime) {
		Log.debugMovement("Rotate around right");
		double centerX;
		double centerY;
		double distRot;
		double angleRot;
		double currentAngle;
		double reverseAngle;
		
		currentAngle = data.angle + Math.PI/2;
		centerX = data.posX + data.distanceBetweenWheel/2*Math.cos(currentAngle);
		centerY = data.posY + data.distanceBetweenWheel/2*Math.sin(currentAngle);
		distRot = data.distanceBetweenWheel/2;
		
		reverseAngle = currentAngle + Math.PI;
		angleRot = (speedLeft*deltaTime)/(data.distanceBetweenWheel);
		double dx = distRot*Math.cos(angleRot + reverseAngle);
		double dy = distRot*Math.sin(angleRot + reverseAngle);
		
		data.posX = centerX + dx;
		data.posY = centerY + dy;
		data.angle += angleRot;
	}

	private void rotateAroundLeft(double speedRight, double speedLeft, int deltaTime) {
		Log.debugMovement("Rotate around left");
		double centerX;
		double centerY;
		double distRot;
		double angleRot;
		double currentAngle;
		double reverseAngle;
		
		currentAngle = data.angle - Math.PI/2;
		centerX = data.posX + data.distanceBetweenWheel/2*Math.cos(currentAngle);
		centerY = data.posY + data.distanceBetweenWheel/2*Math.sin(currentAngle);
		distRot = data.distanceBetweenWheel/2;
		
		reverseAngle = currentAngle + Math.PI;
		angleRot = -(speedRight*deltaTime)/(data.distanceBetweenWheel);
		double dx = distRot*Math.cos(angleRot + reverseAngle);
		double dy = distRot*Math.sin(angleRot + reverseAngle);
		
		data.posX = centerX + dx;
		data.posY = centerY + dy;
		data.angle += angleRot;
	}

	private void moveStraight(double speedRight, double speedLeft, int deltaTime) {
		Log.debugMovement("Move straight");
		//Move in straight line
		double speed = speedRight*deltaTime;
		double dx = speed*Math.cos(data.angle);
		double dy = speed*Math.sin(data.angle);
		data.posX += dx;
		data.posY += dy;
	}
	
	
	
}
