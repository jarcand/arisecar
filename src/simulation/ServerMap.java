package simulation;

import java.awt.Graphics2D;
import java.util.ArrayList;

import simulation.robot.RobotSim;

public class ServerMap {
	
	private final ArrayList<RobotSim> carList = new ArrayList<RobotSim>();
	
	public ServerMap(){
		RobotSim car = new RobotSim("gudra");
		carList.add(car);
	}
	
	public void render(Graphics2D g){
		for(int i=0; i<carList.size(); i++){
			RobotSim ariseCar = carList.get(i);
			ariseCar.render(g);
		}
	}
	
	public void update(int deltaTime){
		for(int i=0; i<carList.size(); i++){
			RobotSim ariseCar = carList.get(i);
			ariseCar.update(deltaTime);
		}
	}
	
	public RobotSim getCar(String user){
		RobotSim result = null;
		for(int i=0; i<carList.size(); i++){
			RobotSim ariseCar = carList.get(i);
			if(ariseCar.getName().equals(user)){
				result = ariseCar;
			}
		}
		return result;
	}

}
