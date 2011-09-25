package robot;

import java.awt.Graphics2D;
import java.util.ArrayList;


import network.message.Message;
import network.message.Movement;
import network.message.Position;
import network.server.EchoServer;

public class ServerMap {
	
	ArrayList<ServerAriseCar> carList = new ArrayList<ServerAriseCar>();
	EchoServer server = new EchoServer(this);
	ArrayList<Message> messageList = new ArrayList<Message>();
	
	
	public void addMessage(Message msg){
		messageList.add(msg);
	}
	
	public void addCar(String user){
		ServerAriseCar ariseCar = new ServerAriseCar(user);
		carList.add(ariseCar);
		Position position = new Position(ariseCar.getName(), ariseCar.x, ariseCar.y, 
				ariseCar.angle, ariseCar.motorRight, ariseCar.motorLeft);
		server.sendPosition(position);
	}
	
	public void render(Graphics2D g){
		for(int i=0; i<carList.size(); i++){
			ServerAriseCar ariseCar = carList.get(i);
			ariseCar.render(g);
		}
	}
	
	public void update(int deltaTime){
		for(int i=0; i<messageList.size(); i++){
			Message msg = messageList.get(i);
			String user = msg.getUser();
			Movement movement = (Movement)msg.getObject();
			ServerAriseCar ariseCar = getCar(user);
			
			ariseCar.applyMovement(movement);
			
			Position position = new Position(ariseCar.getName(), ariseCar.x, ariseCar.y, 
					ariseCar.angle, ariseCar.motorRight, ariseCar.motorLeft);
			server.sendPosition(position);
		}
		messageList.clear();
		for(int i=0; i<carList.size(); i++){
			ServerAriseCar ariseCar = carList.get(i);
			ariseCar.update(deltaTime);
		}
	}
	
	public ServerAriseCar getCar(String user){
		ServerAriseCar result = null;
		for(int i=0; i<carList.size(); i++){
			ServerAriseCar ariseCar = carList.get(i);
			if(ariseCar.getName().equals(user)){
				result = ariseCar;
			}
		}
		return result;
	}

}
