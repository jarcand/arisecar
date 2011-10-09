package user;

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
	
	public void update(int deltaTime){
		xbox.poll();
		double z = xbox.zAxis.getPollData();
		double y = xbox.leftYAxis.getPollData();
		double x = xbox.leftXAxis.getPollData();
		double rx = xbox.rightXAxis.getPollData();
		
		if(Math.abs(z) >= 0.1){

		}else{
			if(Math.abs(rx) >= 0.1){
				//Rotate in place
				//Send something if it change
				if(Math.abs(previousRX-rx) > 0.05){
					//Send data
					previousRX = rx;
					Movement move = new Movement(0, 0, 0, rx);
					controlClient.sendToServer(move);
				}
			}else{
				if(Math.abs(previousRX) >= 0.1){
					previousRX = 0;
					Movement move = new Movement(0, 0, 0, 0);
					controlClient.sendToServer(move);
				}
			}
		}
		
		
	}

}
