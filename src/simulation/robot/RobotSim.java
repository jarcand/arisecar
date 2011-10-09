package simulation.robot;

import hal.HAL;
import hal.HALSim;

import java.awt.Graphics2D;

import network.robot.Robot;

import simulation.robot.module.RobotData;
import simulation.robot.module.RobotDrawing;
import simulation.robot.module.RobotMovement;

public class RobotSim extends Robot{
	
	private final RobotMovement robotMovement;
	private final RobotDrawing robotDrawing;
	private final RobotData robotData;
	private final HALSim halSim;
	
	public RobotSim(String name){
		super(name);
		
		robotData = new RobotData();
		robotMovement = new RobotMovement(this, robotData);
		robotDrawing = new RobotDrawing(this, robotData);
		
		halSim = new HALSim(robotData);
	}
	
	@Override
	public HAL getHAL() {
		return halSim;
	}
	
	public void render(Graphics2D g){
		robotDrawing.render(g);
	}
	
	public void update(int deltaTime){
		robotMovement.updateMovement(deltaTime);
	}
	
}
