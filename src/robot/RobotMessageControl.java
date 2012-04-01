package robot;

import networking.KeyboardMovement;
import networking.Message;
import networking.XboxMovement;

public class RobotMessageControl {
	
	private VehicleModel v;
	private float forward = 0;
	private float turnRate = 0;
	
	public RobotMessageControl(VehicleModel v) {
		this.v = v;
	}
	
	public void handleMessage(Message message) {
		if (message.getValue() instanceof XboxMovement) {
			handleXboxMovement(message.get(XboxMovement.class));
		} else if (message.getValue() instanceof KeyboardMovement) {
			handleKeyboardMovement(message.get(KeyboardMovement.class));
		}
	}
	
	private void handleKeyboardMovement(KeyboardMovement message){
		KeyboardMovement.Direction type = message.direction;
		KeyboardMovement.State state = message.state;
		switch (type) {
			case UP:
				forward = state == KeyboardMovement.State.PRESS ? 1 : 0;
				break;
				
			case DOWN:
				forward = state == KeyboardMovement.State.PRESS ? -1 : 0;
				break;
				
			case LEFT:
				turnRate = state == KeyboardMovement.State.PRESS ? -1 : 0;
				break;
				
			case RIGHT:
				turnRate = state == KeyboardMovement.State.PRESS ? 1 : 0;
				break;
				
			case NONE:
				forward = 0;
				turnRate = 0;
				break;
				
			default:
				break;
		}
		
		// Update the speed and yaw rates
		v.setSpeed(forward);
		v.setYawRate(turnRate / 2);
	}
	
	private void handleXboxMovement(XboxMovement message){
		//double x = message.x;
		//double y = message.y;
		//double z = message.z;
        //double rx = message.rx;
		
		/*
		if(z == 0){
			double speed = rx;
			//hal.setLeftMotor((int) (speed*HALSim.MaxTrustMotor));
			//hal.setRightMotor((int) (-speed*HALSim.MaxTrustMotor));
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
				//hal.setLeftMotor((int) (speed*HALSim.MaxTrustMotor));
				//hal.setRightMotor((int) ((speed*(1-value))*HALSim.MaxTrustMotor));
			}else{
				if(angle < 3*Math.PI/2){
					value = (angle - Math.PI)/(Math.PI/2);
				}else{
					value = (2*Math.PI - angle)/(Math.PI/2);
				}
				//hal.setLeftMotor((int) ((speed*(1-value))*HALSim.MaxTrustMotor));
				//hal.setRightMotor((int) (speed*HALSim.MaxTrustMotor));
			}
		}
		*/
	}
}
