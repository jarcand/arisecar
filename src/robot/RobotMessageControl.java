package robot;

import networking.KeyboardMovement;
import networking.Message;
import networking.MessageFactory;

public class RobotMessageControl {
	
	private VehicleModel v;
	private float forward = 0;
	private float turnRate = 0;
	
	public RobotMessageControl(VehicleModel v) {
		this.v = v;
	}
	
	public void handleMessage(Message message) {
		if (message.getID() == MessageFactory.XboxMovement) {
			handleXboxMovement(message);
		} else if (message.getID() == MessageFactory.KeyboardMovement) {
			handleKeyboardMovement(message);
		}
	}
	
	private void handleKeyboardMovement(Message message){
		int type = message.get(Integer.class, "type");
		int state = message.get(Integer.class, "state");
		switch (type) {
			case KeyboardMovement.Up:
				forward = state == 1 ? 1 : 0;
//	        	v.setMotor1(180);
//	        	v.setMotor2(180);
				break;
				
			case KeyboardMovement.Down:
				forward = state == 1 ? -1 : 0;
//	        	v.setMotor1(0);
//	        	v.setMotor2(0);
				break;
				
			case KeyboardMovement.Left:
				turnRate = state == 1 ? -1 : 0;
//	        	v.setMotor1(30);
//	        	v.setMotor2(150);
				break;
				
			case KeyboardMovement.Right:
				turnRate = state == 1 ? 1 : 0;
//	        	v.setMotor1(150);
//	        	v.setMotor2(30);
				break;
				
			case KeyboardMovement.None:
				forward = 0;
				turnRate = 0;
//	        	v.setMotor1(90);
//	        	v.setMotor2(90);
				break;
				
			default:
				break;
		}
		
		int leftMotor = Math.round((convert(forward, turnRate) + 1) * 90);
		int rightMotor = Math.round((convert(forward, -turnRate) + 1) * 90);
		
		v.setMotor1(leftMotor);
		v.setMotor2(rightMotor);
		
		//System.out.println("Message key : " + type);
		//robot.getNode().sendInfo();
	}
	
	public static float convert(float speed, float spin) {
		final float gamma = 1f;
		if (speed >= 0) {
			return speed + (1 - (spin < 0 ? 0 : speed)) * spin * gamma;
		} else {
			//return speed * (1 + (spin < 0 ? 2 : 1) * spin * gamma) + spin * gamma;
			return speed + spin;
		}
	}
	
	private void handleXboxMovement(Message message){
		@SuppressWarnings("unused")
		double x = message.get(Double.class, "x");
		@SuppressWarnings("unused")
		double y = message.get(Double.class, "y");
		@SuppressWarnings("unused")
		double z = message.get(Double.class, "z");
		@SuppressWarnings("unused")
        double rx = message.get(Double.class, "rx");
		
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
