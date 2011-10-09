package user;

import java.util.ArrayList;

import controller.XBoxController;

import network.client.ControlClient;
import network.message.Message;

public class ControlCenter {
	
	private final ControlClient controlClient;
	private final XBoxInput xboxInput;
	private final XBoxController xboxController;
	
	private final ArrayList<String> robotNameList = new ArrayList<String>();
	
	public ControlCenter(){
		robotNameList.add("gudra");
		
		controlClient = new ControlClient(this);
		xboxController = new XBoxController();
		xboxInput = new XBoxInput(xboxController, controlClient);
	}
	
	public ArrayList<String> getRobotNameList(){ 
		return robotNameList; 
	}
	
	public void handleMessage(Message message){
		
	}

	public void update(int deltaTime) {
		xboxInput.update(deltaTime);
	}

}
