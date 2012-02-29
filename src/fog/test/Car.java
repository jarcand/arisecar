package fog.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

import fog.test.Game.KeyType;
import fog.test.Map.TypeEnum;
import fog.vision.Point;
import fog.vision.PointMap;
import fog.vision.Sonde;


public class Car {
	
	Map map;
	PointMap pointMap;
	VisionCar visionCar;
	
	double posX;
	double posY;
	double radius = 10;
	double direction = 0;
	double speed = 0;
	double turn = 0;
	
	double maxSpeed = 0.1;
	double maxTurn = 0.005;
	
	double startX;
	double startY;
	
	boolean hasMoved = true;
	
	public Car(Map map, PointMap pointMap) {
		this.map = map;
		this.pointMap = pointMap;
		
		boolean placed = false;
		for(int i=6; i<16; i++){
			for(int j = 6; j<16; j++){
				TypeEnum type = map.get(i, j);
				if(type == TypeEnum.empty && !placed){
					posX = i*Map.size + Map.size/2;
					posY = j*Map.size + Map.size/2;
					startX = posX;
					startY = posY;
					placed = true;
				}
			}
		}
		
		visionCar = new VisionCar(this);
		pointMap.setVisionCar(visionCar);
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.blue);
		int x = (int) (posX-radius);
		int y= (int) (posY-radius);
		int width = (int) (radius*2);
		int height = (int) (radius*2);
		g.fillOval(x, y, width, height);
		
		g.setColor(Color.red);
		x = (int) posX;
		y = (int) posY;
		int dirX = (int) (radius*Math.cos(direction));
		int dirY = (int) (radius*Math.sin(direction));
		g.drawLine(x, y, x+dirX, y+dirY);
	}
	
	public void update(int deltaTime){
		//Update position
		if(turn != 0){
			hasMoved = true;
		}
		direction += turn*deltaTime;
		double dist = speed*deltaTime;
		double newPosX = posX + dist * Math.cos(direction);
		double newPosY = posY + dist * Math.sin(direction);
		
		//Keep inside
		boolean keepInside = false;
		if (newPosX - radius < 0 
				|| newPosX + radius > Map.size * Map.number
				|| newPosY - radius < 0
				|| newPosY + radius > Map.size * Map.number) {
			keepInside = true;
		}

		//Check for collision
		boolean noIntersect = true;
		if(!keepInside){
			double a = 0;
			Ellipse2D circle = new Ellipse2D.Double(newPosX-radius-a, newPosY-radius-a, (radius+a)*2, (radius+a)*2);

			for(Rectangle rect : map.rectList){
				if(circle.intersects(rect)){
					noIntersect = false;
					break;
				}
			}	
		}
		
		//Validate position
		if(noIntersect && !keepInside){
			posX = newPosX;
			posY = newPosY;
			hasMoved = true;
		}
		
		//Create point
		if(hasMoved){
			Sonde front = new Sonde(posX, posY, direction);
			Point point = front.send(map.rectList, pointMap.getFogMap(), visionCar);
			if(point != null){
				pointMap.addPoint(new Point(point.x-(int)startX, point.y-(int)startY));
			}
			Sonde left = new Sonde(posX, posY, direction + Math.PI/2);
			point = left.send(map.rectList, pointMap.getFogMap(), visionCar);
			if(point != null){
				pointMap.addPoint(new Point(point.x-(int)startX, point.y-(int)startY));
			}
			Sonde right = new Sonde(posX, posY, direction - Math.PI/2);
			point = right.send(map.rectList, pointMap.getFogMap(), visionCar);
			if(point != null){
				pointMap.addPoint(new Point(point.x-(int)startX, point.y-(int)startY));
			}
			hasMoved = false;
		}
		
		//Create pos
		pointMap.addPos(new Point((int)(posX-startX), (int)(posY-startY)));
	}
	
	public void keyEvent(KeyEvent e, KeyType type){
		if(type == KeyType.Pressed){
			switch(e.getKeyCode()){
			case(KeyEvent.VK_W):
				speed = maxSpeed;
				break;
			case(KeyEvent.VK_A):
				turn = -maxTurn;
				break;
			case(KeyEvent.VK_D):
				turn = maxTurn;
				break;
			}
		}else if(type == KeyType.Released){
			switch(e.getKeyCode()){
			case(KeyEvent.VK_W):
				speed = 0;
				break;
			case(KeyEvent.VK_A):
				turn = 0;
				break;
			case(KeyEvent.VK_D):
				turn = 0;
				break;
			}
		}
		
	}

}
