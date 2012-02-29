package impar.vision;

import java.awt.Rectangle;
import java.util.ArrayList;

import impar.test.Map;
import impar.test.VisionCar;


public class Sonde {
	
	double x;
	double y;
	double angle;
	double speed = 1;
	
	
	public Sonde(double x, double y, double angle){
		this.x = x;
		this.y = y;
		this.angle = angle;
	}
	
	public Point send(ArrayList<Rectangle> rectList, FogMap fogMap, VisionCar visionCar){

		double maxDist = 100;
		double imperfection = 0.50;
		double dist = 0;
		
		boolean collide = false;
		while(!collide){
			dist += speed;
			double nx = dist*Math.cos(angle) + x;
			double ny = dist*Math.sin(angle) + y;
			
			//Check for wall
			for(Rectangle rect : rectList){
				if(rect.contains(nx, ny)){
					collide = true;
				}
			}
			//Check for edge of the map
			if(nx < 0 || nx > Map.size*Map.number || ny < 0 || ny > Map.size*Map.number){
				collide = true;
			}
			//Check for max dist
			if(dist >= maxDist){
				return null;
			}
			
			//Fog discovery
			if(!collide){
				double fogX = nx+visionCar.difPosX;
				double fogY = ny+visionCar.difPosY;
				
				
				
				int fogCellX = (int) (fogX/fogMap.size);
				int fogCellY = (int) (fogY/fogMap.size);
				
				if(fogX < 0){
					fogCellX -= 1;
				}
				if(fogY < 0){
					fogCellY -= 1;
				}

				fogCellX += fogMap.number/2;
				fogCellY += fogMap.number/2;
				

				fogMap.setKnown(fogCellX, fogCellY);
			}
		}
		
		double imparValue = (imperfection * Math.random()) + (1-imperfection/2);
		double imparDist = dist*imparValue;
		
		return (new Point((int)x, (int)y, imparDist, angle));
	}

}
