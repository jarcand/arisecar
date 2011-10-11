package simulation.robot;

import hal.HAL;
import hal.HALSim;

import java.awt.Graphics2D;

import network.robot.Robot;
import network.robot.RobotClient;

import simulation.robot.module.RobotData;
import simulation.robot.module.RobotDrawing;
import simulation.robot.module.RobotMovement;
import simulation.robot.module.RobotMovement;

public class RobotSim implements Robot{
	
	private final String name;
	
	private final RobotMovement robotMovement;
	private final RobotDrawing robotDrawing;
	private final RobotData robotData;
	private final HALSim halSim;
	private double startTimeInSeconds;
	
	public RobotSim(String name){
		this.name = name;
		startTimeInSeconds = System.currentTimeMillis()/1000;
		
		robotData = new RobotData();
		robotMovement = new RobotMovement(this, robotData);
		robotDrawing = new RobotDrawing(this, robotData);
		
		halSim = new HALSim(robotData);
		
		new RobotClient(this);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public HAL getHAL() {
		return halSim;
	}
	
	public void render(Graphics2D g){
		robotDrawing.render(g);
	}
	
	public void update(int deltaTime){
		checkBatteryLife();
		if(robotData.batteryLife !=0)
			robotMovement.updateMovement(deltaTime);
	}

	private void checkBatteryLife()
	{
		double currentTimeInSeconds = System.currentTimeMillis()/1000;
		double timeElapsed = currentTimeInSeconds - startTimeInSeconds;
		for (int row = 0; row < robotData.batteryLives.length; row++) {
                if( robotData.batteryLives[row][0] == timeElapsed)
                {
                	robotData.batteryLife = robotData.batteryLives[row][1];      
                }
            }
	}
	
	
}
