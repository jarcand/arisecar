package impar.vision;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import impar.test.VisionCar;
import impar.vision.GeneralMap.Cell;


public class PointMap {
	
	ArrayList<Point> posList = new ArrayList<Point>();
	
	private GeneralMap pointData = new GeneralMap();
	
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
		g.setStroke(new BasicStroke(3));
		for(Cell cell : pointData.getCellList()){
			for(Point point : cell.getList()){
				if(point.validity < 10){
					g.setColor(Color.gray);
					g.drawLine(point.x(), point.y(), point.x()+1, point.y());
				}
			}
		}
		
		for(Cell cell : pointData.getCellList()){
			for(Point point : cell.getList()){
				if(point.validity >= 10){
					g.setColor(Color.green);
					g.drawLine(point.x(), point.y(), point.x()+1, point.y());
				}
			}
		}
		
		for(Point point : pointData.getCollisionList()){
			if(point.validity < 10 && pointData.time-point.time < GeneralMap.MinimumCollision){
				g.setColor(Color.red);
				g.drawLine(point.x(), point.y(), point.x()+1, point.y());
			}
		}
		
		//Draw path
		g.setColor(Color.orange);
		for(Point point : posList){
			//g.drawLine(point.x, point.y, point.x+1, point.y);
		}
		
		//Draw car
		visionCar.draw(g);
	}
	
	public void addPoint(Point point){
		pointData.add(point);
	}
	
	public void addPos(Point point){
		posList.add(point);
	}
	
	public void update(int deltaTime){
		pointData.update(deltaTime);
	}

}
