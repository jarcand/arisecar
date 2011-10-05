package network.robot;

import util.Math2;
import network.message.Movement;
import hal.HAL;

public class RobotMessageControl {
	
	private final RobotClient robotClient;
	private final HAL hal;
	
	public RobotMessageControl(RobotClient robotClient, Robot robot){
		this.robotClient = robotClient;
		this.hal = robot.getHAL();
	}
	
	public void handleMessage(Object message) {
		if(message instanceof Movement){
			handleMovement((Movement)message);
		}
	}
	
	private void handleMovement(Movement movement){

		double speed = 0.060*movement.getZ();
		double x = movement.getX();
		double y = movement.getY();
		double d = Math.sqrt(x*x+y*y);
		double angle;
		if(d > 0.8){
			angle = Math2.findAngle(y, x);
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
			hal.setLeftMotor((int) speed);
			hal.setRightMotor((int) (speed*(1-value*0.5)));
		}else{
			if(angle < 3*Math.PI/2){
				value = (angle - Math.PI)/(Math.PI/2);
			}else{
				value = (2*Math.PI - angle)/(Math.PI/2);
			}
			hal.setLeftMotor((int) (speed*(1-value*0.5)));
			hal.setRightMotor((int) speed);
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
