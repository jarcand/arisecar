package user;

import util.Math2;
import network.client.ControlClient;
import network.message.Movement;
import controller.XBoxController;

public class XBoxInput {
	
	private final XBoxController xbox;
	private final ControlClient controlClient;
	
	public XBoxInput(XBoxController xbox, ControlClient controlClient){
		this.xbox = xbox;
		this.controlClient = controlClient;
	}
	
	double previousRX = 0;
	double previousZ = 0;
	double previousAngle = 0;
	double previousD = 0;
	
	public void update(int deltaTime){
		xbox.poll();
		double z = xbox.zAxis.getPollData();
		double y = xbox.leftYAxis.getPollData();
		double x = xbox.leftXAxis.getPollData();
		double rx = xbox.rightXAxis.getPollData();
		
		if(Math.abs(z) >= 0.1){
			boolean hasChanged = Math.abs(previousZ - z) > 0.05;
			
			if(Math.sqrt(x*x + y*y) > 0.1){
				double angle = Math2.findAngle(x, y);
				if(Math.abs(angle-previousAngle) > Math.PI/32 || hasChanged){
					previousAngle = angle;
					previousZ = z;
					previousD = 1;
					Movement move = new Movement(x, y, z, 0);
					controlClient.sendToServer(move);
				}
			}else if(hasChanged || previousD == 1){
				previousAngle = 0;
				previousZ = z;
				previousD = 0;
				Movement move = new Movement(0, 0, z, 0);
				controlClient.sendToServer(move);
			}
		}else if(Math.abs(rx) >= 0.2){
			//Rotate in place
			//Send something if it change
			if(Math.abs(previousRX-rx) > 0.05){
				//Send data
				previousRX = rx;
				Movement move = new Movement(0, 0, 0, rx);
				controlClient.sendToServer(move);
			}
		}else if(Math.abs(previousRX) >= 0.2 || Math.abs(previousZ) >= 0.1){
			previousRX = 0;
			previousZ = 0;
			previousAngle = 0;
			Movement move = new Movement(0, 0, 0, 0);
			controlClient.sendToServer(move);
		}
		
		
	}

}
