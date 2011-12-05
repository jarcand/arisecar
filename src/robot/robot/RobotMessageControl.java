package robot;

import ca.ariselab.devices.serial.PontoonArduino;
import ca.ariselab.lib.serialdevices.SerialDeviceID;
import ca.ariselab.lib.serialdevices.SerialDeviceInitException;
import factory.MessageFactory;
import factory.message.KeyboardMovement;
import server.Message;
import util.Log;
import util.Math2;

public class RobotMessageControl {
	
	private final Robot robot;
	private PontoonArduino pa;
	
	public RobotMessageControl(Robot robot){
		this.robot = robot;
		try {
	        pa = new PontoonArduino(new SerialDeviceID(0x70)) {
	        	private int i = 0;
	            protected void inputsUpdated() {
	            	i++;
	            	if (i % 20 == 0) {
	            		System.out.print(".");
	            	}
	            }
	        };
        } catch (SerialDeviceInitException e) {
	        e.printStackTrace();
        }
	}
	
	public void handleMessage(Message message) {
		if(message.getID() == MessageFactory.XboxMovement){
			handleMovement(message);
		}else if(message.getID() == MessageFactory.KeyboardMovement){
			handleMovementKey(message);
		}
	}
	
	private void handleMovementKey(Message message){
		int type = message.get(Integer.class, "type");
		System.out.println(type);
		switch (type) {
			case KeyboardMovement.Up:
	        	pa.setMotorFore(180);
	        	pa.setMotorAft(180);
				break;
				
			case KeyboardMovement.Down:
	        	pa.setMotorFore(0);
	        	pa.setMotorAft(0);
				break;
				
			case KeyboardMovement.Left:
	        	pa.setMotorFore(30);
	        	pa.setMotorAft(150);
				break;
				
			case KeyboardMovement.Right:
	        	pa.setMotorFore(150);
	        	pa.setMotorAft(30);
				break;
				
			case KeyboardMovement.None:
	        	pa.setMotorFore(90);
	        	pa.setMotorAft(90);
				break;
				
			default:
				break;
		}
		//System.out.println("Message key : " + type);
		//robot.getNode().sendInfo();
	}
	
	private void handleMovement(Message message){
		double x = message.get(Double.class, "x");
		double y = message.get(Double.class, "y");
		double z = message.get(Double.class, "z");
		double rx = message.get(Double.class, "rx");
		
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
	}

}
