package network.robot;

import util.Log;
import util.Math2;
import network.message.Message;
import network.message.MessageCreator;
import network.message.Movement;
import hal.HAL;
import hal.HALSim;

public class RobotMessageControl {
	
	private final RobotClient robotClient;
	private final HAL hal;
	
	public RobotMessageControl(RobotClient robotClient, Robot robot){
		this.robotClient = robotClient;
		this.hal = robot.getHAL();
	}
	
	public void handleMessage(Message message) {
		Log.println(message);
		if(message.getID() == MessageCreator.XboxMovement){
			handleMovement(message);
		}
	}
	
	private void handleMovement(Message message){
		double x = message.get(Double.class, "x");
		double y = message.get(Double.class, "y");
		double z = message.get(Double.class, "z");
		double rx = message.get(Double.class, "rx");
		
		if(z == 0){
			double speed = rx*0.06;
			hal.setLeftMotor((int) (speed*HALSim.MaxTrustMotor));
			hal.setRightMotor((int) (-speed*HALSim.MaxTrustMotor));
		}else{

			double speed = 0.060*z;
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
				hal.setLeftMotor((int) (speed*HALSim.MaxTrustMotor));
				hal.setRightMotor((int) ((speed*(1-value*0.5))*HALSim.MaxTrustMotor));
			}else{
				if(angle < 3*Math.PI/2){
					value = (angle - Math.PI)/(Math.PI/2);
				}else{
					value = (2*Math.PI - angle)/(Math.PI/2);
				}
				hal.setLeftMotor((int) ((speed*(1-value*0.5))*HALSim.MaxTrustMotor));
				hal.setRightMotor((int) (speed*HALSim.MaxTrustMotor));
			}
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
