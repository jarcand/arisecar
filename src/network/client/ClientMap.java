package network.client;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import robot.ServerAriseCar;

import network.message.Movement;
import network.message.Position;


public class ClientMap {
	
	private final SimpleClient client;
	private final String name;
	private ArrayList<ServerAriseCar> carList = new ArrayList<ServerAriseCar>();
	
	public ClientMap(String name){
		client = new SimpleClient(this);
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void addPosition(Position position){
		String name = position.getName();
		ServerAriseCar ariseCar = getCar(name);
		if(ariseCar == null){
			ariseCar = new ServerAriseCar(name);
			carList.add(ariseCar);
		}
		ariseCar.setX(position.getX());
		ariseCar.setY(position.getY());
		ariseCar.setAngle(position.getAngle());
		ariseCar.setMotorRight(position.getMotorRight());
		ariseCar.setMotorLeft(position.getMotorLeft());
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
	
	public void keyPressed(KeyEvent evt){
		Movement movement = new Movement(evt, Movement.IsPressed);
		try {
			client.sendToServer(movement);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void keyReleased(KeyEvent evt){
		Movement movement = new Movement(evt, Movement.IsReleased);
		try {
			client.sendToServer(movement);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics2D g){
		for(int i=0; i<carList.size(); i++){
			ServerAriseCar ariseCar = carList.get(i);
			ariseCar.render(g);
		}
	}
	
	public void update(int deltaTime){
		for(int i=0; i<carList.size(); i++){
			ServerAriseCar ariseCar = carList.get(i);
			ariseCar.update(deltaTime);
		}
	}

}
