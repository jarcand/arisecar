package fog.vision;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import fog.test.VisionCar;


public class PointMap {
	
	ArrayList<Point> pointList = new ArrayList<Point>();
	ArrayList<Point> posList = new ArrayList<Point>();
	
	VisionCar visionCar;
	FogMap fogMap;
	
	public void setVisionCar(VisionCar visionCar){
		this.visionCar = visionCar;
		fogMap = new FogMap();
	}
	
	public FogMap getFogMap(){
		return fogMap;
	}
	
	public void draw(Graphics2D g){
		
		
		g.translate(660*1.5, 660*0.5);
		
		//Draw fog of war
		fogMap.draw(g);
		
		//Draw wall
		g.setColor(Color.green);
		g.setStroke(new BasicStroke(3));
		for(Point point : pointList){
			g.drawLine(point.x, point.y, point.x+1, point.y);
		}
		
		//Draw path
		g.setColor(Color.orange);
		for(Point point : posList){
			g.drawLine(point.x, point.y, point.x+1, point.y);
		}
		
		//Draw car
		visionCar.draw(g);
	}
	
	public void addPoint(Point point){
		pointList.add(point);
	}
	
	public void addPos(Point point){
		posList.add(point);
	}

}
