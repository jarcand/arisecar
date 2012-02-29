package impar.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;


public class VisionCar {
	
	Car car;
	
	public double difPosX;
	public double difPosY;
	
	public VisionCar(Car car){
		this.car = car;
		difPosX = -car.posX;
		difPosY = -car.posY;
	}
	
	public void draw(Graphics2D g){
		g.setStroke(new BasicStroke(1));
		
		g.setColor(Color.blue);
		int x = (int) (car.posX+difPosX-car.radius);
		int y= (int) (car.posY+difPosY-car.radius);
		int width = (int) (car.radius*2);
		int height = (int) (car.radius*2);
		g.fillOval(x, y, width, height);
		
		g.setColor(Color.red);
		x = (int) (car.posX+difPosX);
		y = (int) (car.posY+difPosY);
		int dirX = (int) (car.radius*Math.cos(car.direction));
		int dirY = (int) (car.radius*Math.sin(car.direction));
		g.drawLine(x, y, x+dirX, y+dirY);
	}

}
