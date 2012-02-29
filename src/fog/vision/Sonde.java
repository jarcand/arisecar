package fog.vision;

import java.awt.Rectangle;
import java.util.ArrayList;

import fog.test.Map;
import fog.test.VisionCar;


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

		double maxDist = 300;
		double dist = 0;
		
		boolean collide = false;
		while(!collide){
			dist += speed;
			x += speed*Math.cos(angle);
			y += speed*Math.sin(angle);
			
			//Check for wall
			for(Rectangle rect : rectList){
				if(rect.contains(x, y)){
					collide = true;
				}
			}
			//Check for edge of the map
			if(x < 0 || x > Map.size*Map.number || y < 0 || y > Map.size*Map.number){
				collide = true;
			}
			//Check for max dist
			if(dist >= maxDist){
				return null;
			}
			
			//Fog discovery
			if(!collide){
				double fogX = x+visionCar.difPosX;
				double fogY = y+visionCar.difPosY;
				
				
				
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
		
		return (new Point((int)x, (int)y));
	}

}
