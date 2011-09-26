package simulation;

import java.awt.Graphics2D;
import java.util.ArrayList;

import simulation.robot.AriseCar;

public class ServerMap {
	
	private final ArrayList<AriseCar> carList = new ArrayList<AriseCar>();
	
	public ServerMap(){
		AriseCar car = new AriseCar("gudra");
		carList.add(car);
	}
	
	public void render(Graphics2D g){
		for(int i=0; i<carList.size(); i++){
			AriseCar ariseCar = carList.get(i);
			ariseCar.render(g);
		}
	}
	
	public void update(int deltaTime){
		for(int i=0; i<carList.size(); i++){
			AriseCar ariseCar = carList.get(i);
			ariseCar.update(deltaTime);
		}
	}
	
	public AriseCar getCar(String user){
		AriseCar result = null;
		for(int i=0; i<carList.size(); i++){
			AriseCar ariseCar = carList.get(i);
			if(ariseCar.getName().equals(user)){
				result = ariseCar;
			}
		}
		return result;
	}

}
