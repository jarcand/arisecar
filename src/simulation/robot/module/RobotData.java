package simulation.robot.module;

public class RobotData {
	
	public final double MaxRotSpeed = 0.03;
	
	public double rotSpeedRight = 0.0;
	public double rotSpeedLeft = -0.01;
	public double circRight = 10;
	public double circLeft = 10;
	public double distanceBetweenWheel = 60;
	public double size = 100;
	public double angle = 0;
	public double posX = 200;
	public double posY = 200;
	
	//[row][0] = time elapsed(measured in seconds), [row][1] = batteryLife at time [row][0]
	public int batteryLives[][] = new int[][]{{10,98},{20,97}, {30, 96} , {50, 93}, {200, 76}, {300, 47}, {800, 0}};
	public double batteryLife = 100.0;
}
