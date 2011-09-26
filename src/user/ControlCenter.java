package user;

import java.util.ArrayList;

import network.client.ControlClient;
import network.message.Message;

public class ControlCenter {
	
	private final ControlClient controlClient;
	
	private final ArrayList<String> robotNameList = new ArrayList<String>();
	
	public ControlCenter(){
		controlClient = new ControlClient(this);
	}
	
	public ArrayList<String> getRobotNameList(){ 
		return robotNameList; 
	}
	
	public void handleMessage(Message message){
		
	}

}
