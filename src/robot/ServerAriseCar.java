package robot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import network.message.Movement;

public class ServerAriseCar {
	
	private String name;

	double x = 200;
	double y = 200;
	int size = 100;
	double angle = 0;
	
	int distanceMotor = 30;
	int sizeMotor = 5;
	int sizeDot = 25;
	
	int motorRight = 0;
	int motorLeft = 0;
	
	double speed = 0.1;
	
	public ServerAriseCar(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void setAngle(double angle){
		this.angle = angle;
	}
	
	public void setMotorRight(int motorRight){
		this.motorRight = motorRight;
	}
	
	public void setMotorLeft(int motorLeft){
		this.motorLeft = motorLeft;
	}
	
	public void applyMovement(Movement movement) {
		KeyEvent e = movement.getKeyEvent();
		
		if(movement.isPressed()){
			if(e.getKeyCode() == KeyEvent.VK_F){
				motorRight = 1;
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				motorRight = -1;
			}
			if(e.getKeyCode() == KeyEvent.VK_J){
				motorLeft = 1;
			}
			if(e.getKeyCode() == KeyEvent.VK_K){
				motorLeft = -1;
			}
		}
		
		if(movement.isReleased()){
			if(e.getKeyCode() == KeyEvent.VK_F){
				motorRight = 0;
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				motorRight = 0;
			}
			if(e.getKeyCode() == KeyEvent.VK_J){
				motorLeft = 0;
			}
			if(e.getKeyCode() == KeyEvent.VK_K){
				motorLeft = 0;
			}
		}
	}
	
	public void hello(){
		
	}
	
	public void render(Graphics2D g){
		//Draw car
		g.setColor(Color.blue);
		g.fillOval((int)x-size/2, (int)y-size/2, size, size);
		
		//Draw direction front dot
		g.setColor(Color.red);
		double dist = sizeDot;
		double tmpX = dist*Math.cos(angle);
		double tmpY = dist*Math.sin(angle);
		g.fillOval((int)(x+tmpX-sizeDot/2), (int)(y+tmpY-sizeDot/2), sizeDot, sizeDot);
		
		//Draw right wheel
		g.setColor(Color.green);
		double rAngle = angle + Math.PI/2;
		double rcx = x+distanceMotor*Math.cos(rAngle);
		double rcy = y+distanceMotor*Math.sin(rAngle);
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
		double lAngle = angle - Math.PI/2;
		double lcx = x+distanceMotor*Math.cos(lAngle);
		double lcy = y+distanceMotor*Math.sin(lAngle);
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
		g.drawString(name, (int)x-25, (int)y+5);
	}
	
	public void update(int deltaTime){
		//Move forward
		if(motorRight == 1 && motorLeft == 1){
			double dist = speed*deltaTime;
			double tmpX = dist*Math.cos(angle);
			double tmpY = dist*Math.sin(angle);
			x += tmpX;
			y += tmpY;
		}
		//Move backward
		else if(motorRight == -1 && motorLeft == -1){
			double dist = -speed*deltaTime;
			double tmpX = dist*Math.cos(angle);
			double tmpY = dist*Math.sin(angle);
			x += tmpX;
			y += tmpY;
		}
		//Rotate right from center
		else if(motorRight == 1 && motorLeft == -1){
			double dist = distanceMotor;
			double deltaRot = ((speed*deltaTime)/(2*Math.PI*dist)) * (2*Math.PI);
			angle += deltaRot;
		}
		//Rotate left from center
		else if(motorRight == -1 && motorLeft == 1){
			double dist = distanceMotor;
			double deltaRot = ((-speed*deltaTime)/(2*Math.PI*dist)) * (2*Math.PI);
			angle += deltaRot;
		}
		//Turn right
		else if(motorRight == 1 && motorLeft == 0){
			double dist = distanceMotor*1;
			double leftAngle = angle + Math.PI/2;
			double cx = x + distanceMotor*Math.cos(leftAngle);
			double cy = y + distanceMotor*Math.sin(leftAngle);
			double deltaRot = ((speed*deltaTime)/(2*Math.PI*dist)) * (2*Math.PI);
			double centerAngle = angle + Math.PI/2 + Math.PI;
			centerAngle += deltaRot;
			double dx = cx + distanceMotor*Math.cos(centerAngle);
			double dy = cy + distanceMotor*Math.sin(centerAngle);
			x = dx;
			y = dy;
			angle += deltaRot;
		}
		//Turn left
		else if(motorRight == 0 && motorLeft == 1){
			double dist = distanceMotor*1;
			double rightAngle = angle - Math.PI/2;
			double cx = x + distanceMotor*Math.cos(rightAngle);
			double cy = y + distanceMotor*Math.sin(rightAngle);
			double deltaRot = ((-speed*deltaTime)/(2*Math.PI*dist)) * (2*Math.PI);
			double centerAngle = angle - Math.PI/2 + Math.PI;
			centerAngle += deltaRot;
			double dx = cx + distanceMotor*Math.cos(centerAngle);
			double dy = cy + distanceMotor*Math.sin(centerAngle);
			x = dx;
			y = dy;
			angle += deltaRot;
		}
		//Turn right backward
		else if(motorRight == -1 && motorLeft == 0){
			double dist = distanceMotor*1;
			double leftAngle = angle + Math.PI/2;
			double cx = x + distanceMotor*Math.cos(leftAngle);
			double cy = y + distanceMotor*Math.sin(leftAngle);
			double deltaRot = ((-speed*deltaTime)/(2*Math.PI*dist)) * (2*Math.PI);
			double centerAngle = angle + Math.PI/2 + Math.PI;
			centerAngle += deltaRot;
			double dx = cx + distanceMotor*Math.cos(centerAngle);
			double dy = cy + distanceMotor*Math.sin(centerAngle);
			x = dx;
			y = dy;
			angle += deltaRot;
		}
		//Turn left backward
		else if(motorRight == 0 && motorLeft == -1){
			double dist = distanceMotor*1;
			double rightAngle = angle - Math.PI/2;
			double cx = x + distanceMotor*Math.cos(rightAngle);
			double cy = y + distanceMotor*Math.sin(rightAngle);
			double deltaRot = ((speed*deltaTime)/(2*Math.PI*dist)) * (2*Math.PI);
			double centerAngle = angle - Math.PI/2 + Math.PI;
			centerAngle += deltaRot;
			double dx = cx + distanceMotor*Math.cos(centerAngle);
			double dy = cy + distanceMotor*Math.sin(centerAngle);
			x = dx;
			y = dy;
			angle += deltaRot;
		}
	}
}
