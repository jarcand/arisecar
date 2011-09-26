package simulation.robot;

import java.awt.Graphics2D;

import network.message.Movement;

import controller.XBoxController;

import simulation.robot.module.RobotData;
import simulation.robot.module.RobotDrawing;
import simulation.robot.module.RobotMovement;

public class AriseCar {
	
	private String name;
	
	private final RobotMovement robotMovement;
	private final RobotDrawing robotDrawing;
	private final RobotData robotData;
	
	private XBoxController xbox = new XBoxController();
	
	public AriseCar(String name){
		this.name = name;
		
		robotData = new RobotData();
		robotMovement = new RobotMovement(this, robotData);
		robotDrawing = new RobotDrawing(this, robotData);
	}
	
	public void render(Graphics2D g){
		robotDrawing.render(g);
	}
	
	public void update(int deltaTime){
		xbox.poll();
		xbox.zAxis.getPollData();
		Movement movement = new Movement(xbox.leftXAxis.getPollData(), xbox.leftYAxis.getPollData(), xbox.zAxis.getPollData());
		robotMovement.handleMessage(movement);
		robotMovement.updateMovement(deltaTime);
	}
	
	public String getName(){
		return name;
	}
	
}
