package fog.test;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import fog.vision.PointMap;


public class Game {
	
	public enum KeyType{
		Pressed, Released;
	}
	
	PointMap pointMap = new PointMap();
	Map map = new Map(this);
	
	
	public void draw(Graphics2D g) {
		map.draw(g);
		pointMap.draw(g);
	}
	
	public void update(int deltaTime){
		map.update(deltaTime);
	}
	
	public void keyEvent(KeyEvent e, KeyType type){
		map.keyEvent(e, type);
	}

}
