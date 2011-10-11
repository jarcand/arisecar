package simulation.robot.module;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import simulation.robot.RobotSim;

public class RobotDrawing {
	
	private final RobotSim robot;
	private final RobotData data;
	
	private final int sizeMotor = 5;
	private final int sizeDot = 25;
	
	public RobotDrawing(RobotSim robot, RobotData data){
		this.robot = robot;
		this.data = data;
	}
	
	public void render(Graphics2D g){
		//Draw car
		g.setColor(Color.blue);
		g.fillOval((int)(data.posX-data.size/2), (int)(data.posY-data.size/2), (int)data.size, (int)data.size);
		
		//Draw direction front dot
		g.setColor(Color.red);
		double dist = sizeDot;
		double tmpX = dist*Math.cos(data.angle);
		double tmpY = dist*Math.sin(data.angle);
		g.fillOval((int)(data.posX+tmpX-sizeDot/2), (int)(data.posY+tmpY-sizeDot/2), sizeDot, sizeDot);
		
		//Draw right wheel
		g.setColor(Color.green);
		double rAngle = data.angle + Math.PI/2;
		double rcx = data.posX+data.distanceBetweenWheel/2*Math.cos(rAngle);
		double rcy = data.posY+data.distanceBetweenWheel/2*Math.sin(rAngle);
		double length = sizeDot;
		double rbAngle = rAngle - Math.PI/2;
		double rtAngle = rAngle + Math.PI/2;
		double rbx = rcx + length*Math.cos(rbAngle);
		double rby = rcy + length*Math.sin(rbAngle);
		double rtx = rcx + length*Math.cos(rtAngle);
		double rty = rcy + length*Math.sin(rtAngle);
		g.setStroke(new BasicStroke(sizeMotor));
		g.drawLine((int)rbx, (int)rby, (int)rtx, (int)rty);
		
		//Draw left wheel
		g.setColor(Color.black);
		double lAngle = data.angle - Math.PI/2;
		double lcx = data.posX+data.distanceBetweenWheel/2*Math.cos(lAngle);
		double lcy = data.posY+data.distanceBetweenWheel/2*Math.sin(lAngle);
		double lbAngle = lAngle - Math.PI/2;
		double ltAngle = lAngle + Math.PI/2;
		double lbx = lcx + length*Math.cos(lbAngle);
		double lby = lcy + length*Math.sin(lbAngle);
		double ltx = lcx + length*Math.cos(ltAngle);
		double lty = lcy + length*Math.sin(ltAngle);
		g.setStroke(new BasicStroke(sizeMotor));
		g.drawLine((int)lbx, (int)lby, (int)ltx, (int)lty);
		
		//Draw name
		g.setFont(new Font("stupidFont", Font.BOLD, 16));
		g.setColor(Color.white);
		g.drawString(robot.getName(), (int)data.posX-25, (int)data.posY+5);
		
		//Draw battery power
		g.setColor(Color.yellow);
		g.drawString("B: "+data.batteryLife+"%", (int)data.posX-30, (int)data.posY+25);
	}

}
