package simulation.robot.module;

import simulation.robot.RobotSim;

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
