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
			double speed = rx;
			hal.setLeftMotor((int) (speed*HALSim.MaxTrustMotor));
			hal.setRightMotor((int) (-speed*HALSim.MaxTrustMotor));
		}else{

			double speed = z;
			double angle;
			if(x == 0 && y == 0){
				angle = 0;
			}else{
				angle = Math2.findAngle(-y, x);
			}

			Log.println(x + " : " + y + " : " + z + angle/Math.PI/2);
			double value;
			if(angle >= 0 && angle <= Math.PI){
				if(angle <= Math.PI/2){
					value = angle/(Math.PI/2);
				}else{
					value = (Math.PI - angle)/(Math.PI/2);
				}
				hal.setLeftMotor((int) (speed*HALSim.MaxTrustMotor));
				hal.setRightMotor((int) ((speed*(1-value))*HALSim.MaxTrustMotor));
			}else{
				if(angle < 3*Math.PI/2){
					value = (angle - Math.PI)/(Math.PI/2);
				}else{
					value = (2*Math.PI - angle)/(Math.PI/2);
				}
				hal.setLeftMotor((int) ((speed*(1-value))*HALSim.MaxTrustMotor));
				hal.setRightMotor((int) (speed*HALSim.MaxTrustMotor));
			}
		}
	}
	
	

}
