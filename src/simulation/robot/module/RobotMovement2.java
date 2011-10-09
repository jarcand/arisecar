package simulation.robot.module;

import simulation.robot.RobotSim;

public class RobotMovement2 {
	
	private final RobotSim robot;
	private final RobotData data;
	
	public RobotMovement2(RobotSim robot, RobotData data){
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
		
		double factor = speedRight/speedLeft;
		double dist = factor*data.distanceBetweenWheel/(1-factor);
		distRot = dist + data.distanceBetweenWheel/2;
		
		currentAngle = data.angle - Math.PI/2;
		centerX = data.posX + distRot*Math.cos(currentAngle);
		centerY = data.posY + distRot*Math.sin(currentAngle);
		
		angleRot = speedRight*deltaTime / (dist);
		double dx = distRot*Math.cos(angleRot + currentAngle);
		double dy = distRot*Math.sin(angleRot + currentAngle);
		
		data.posX = centerX + dx;
		data.posY = centerY + dy;
		data.angle += angleRot;
	}

	private void rotateOutsideLeft(double speedRight, double speedLeft, int deltaTime) {
		//The center is outside on the left side
		//This mean that speedRight > speedLeft in abs
		double centerX;
		double centerY;
		double distRot;
		double angleRot;
		double currentAngle;
		
		double factor = speedLeft/speedRight;
		double dist = factor*data.distanceBetweenWheel/(1-factor);
		distRot = dist + data.distanceBetweenWheel/2;
		
		currentAngle = data.angle+Math.PI/2;
		centerX = data.posX + distRot*Math.cos(currentAngle);
		centerY = data.posY + distRot*Math.sin(currentAngle);
		
		angleRot = speedLeft*deltaTime / (dist);
		double dx = distRot*Math.cos(angleRot + currentAngle);
		double dy = distRot*Math.sin(angleRot + currentAngle);
		
		data.posX = centerX + dx;
		data.posY = centerY + dy;
		data.angle += angleRot;
	}

	private void rotateInsideRight(double speedRight, double speedLeft, int deltaTime) {
		//The center is between the 2 wheels near the right one.
		//This mean that speedLeft > speedRight in abs.
		double centerX;
		double centerY;
		double distRot;
		double angleRot;
		double currentAngle;
		
		double factor = Math.abs(speedRight)/(Math.abs(speedRight)+Math.abs(speedLeft));
		double dist = factor*data.distanceBetweenWheel;
		distRot = data.distanceBetweenWheel/2 - dist;
		
		currentAngle = data.angle-Math.PI/2;
		centerX = data.posX + distRot*Math.cos(currentAngle);
		centerY = data.posY + distRot*Math.sin(currentAngle);
		
		angleRot = speedRight*deltaTime/(dist);
		double dx = distRot*Math.cos(angleRot + currentAngle);
		double dy = distRot*Math.sin(angleRot + currentAngle);
		
		data.posX = centerX + dx;
		data.posY = centerY + dy;
		data.angle += angleRot;
	}

	private void rotateInsideLeft(double speedRight, double speedLeft, int deltaTime) {
		//The center is between the 2 wheels near the right one.
		//This mean that speedLeft > speedRight in abs.
		double centerX;
		double centerY;
		double distRot;
		double angleRot;
		double currentAngle;
		
		double factor = Math.abs(speedLeft)/(Math.abs(speedRight)+Math.abs(speedLeft));
		double dist = factor*data.distanceBetweenWheel;
		distRot = data.distanceBetweenWheel/2 - dist;
		
		currentAngle = data.angle+Math.PI/2;
		centerX = data.posX + distRot*Math.cos(currentAngle);
		centerY = data.posY + distRot*Math.sin(currentAngle);
		
		angleRot = speedRight*deltaTime/(dist);
		double dx = distRot*Math.cos(angleRot + currentAngle);
		double dy = distRot*Math.sin(angleRot + currentAngle);
		
		data.posX = centerX + dx;
		data.posY = centerY + dy;
		data.angle += angleRot;
	}

	private void rotateAroundRight(double speedRight, double speedLeft, int deltaTime) {
		double centerX;
		double centerY;
		double distRot;
		double angleRot;
		double currentAngle;
		
		currentAngle = data.angle + Math.PI/2;
		centerX = data.posX + data.distanceBetweenWheel/2*Math.cos(currentAngle);
		centerY = data.posY + data.distanceBetweenWheel/2*Math.sin(currentAngle);
		distRot = data.distanceBetweenWheel/2;
		angleRot = (speedLeft*deltaTime)/(data.distanceBetweenWheel);
		
		double dx = distRot*Math.cos(angleRot + currentAngle);
		double dy = distRot*Math.sin(angleRot + currentAngle);
		
		data.posX = centerX + dx;
		data.posY = centerY + dy;
		data.angle += angleRot;
	}

	private void rotateAroundLeft(double speedRight, double speedLeft, int deltaTime) {
		double centerX;
		double centerY;
		double distRot;
		double angleRot;
		double currentAngle;
		
		currentAngle = data.angle - Math.PI/2;
		centerX = data.posX + data.distanceBetweenWheel/2*Math.cos(currentAngle);
		centerY = data.posY + data.distanceBetweenWheel/2*Math.sin(currentAngle);
		distRot = data.distanceBetweenWheel/2;
		angleRot = (speedRight*deltaTime)/(data.distanceBetweenWheel);
		
		double dx = distRot*Math.cos(angleRot + currentAngle);
		double dy = distRot*Math.sin(angleRot + currentAngle);
		
		data.posX = centerX + dx;
		data.posY = centerY + dy;
		data.angle += angleRot;
	}

	private void moveStraight(double speedRight, double speedLeft, int deltaTime) {
		//Move in straight line
		double speed = speedRight*deltaTime;
		double dx = speed*Math.cos(data.angle);
		double dy = speed*Math.sin(data.angle);
		data.posX += dx;
		data.posY += dy;
	}
	
	
	
}
