package robot;

import networking.KeyboardMovement;
import networking.Message;
import networking.MessageFactory;

public class RobotMessageControl {
	
	private VehicleModel v;
	
	public RobotMessageControl(VehicleModel v) {
		this.v = v;
	}
	
	public void handleMessage(Message message) {
		if(message.getID() == MessageFactory.XboxMovement){
			handleXboxMovement(message);
		}else
		if(message.getID() == MessageFactory.KeyboardMovement){
			handleKeyboardMovement(message);
		}
	}
	
	private void handleKeyboardMovement(Message message){
		int type = message.get(Integer.class, "type");
		System.out.println(type);
		switch (type) {
			case KeyboardMovement.Up:
	        	v.setMotor1(180);
	        	v.setMotor2(180);
				break;
				
			case KeyboardMovement.Down:
	        	v.setMotor1(0);
	        	v.setMotor2(0);
				break;
				
			case KeyboardMovement.Left:
	        	v.setMotor1(30);
	        	v.setMotor2(150);
				break;
				
			case KeyboardMovement.Right:
	        	v.setMotor1(150);
	        	v.setMotor2(30);
				break;
				
			case KeyboardMovement.None:
	        	v.setMotor1(90);
	        	v.setMotor2(90);
				break;
				
			default:
				break;
		}
		//System.out.println("Message key : " + type);
		//robot.getNode().sendInfo();
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
