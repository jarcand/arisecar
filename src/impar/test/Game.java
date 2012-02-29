package impar.test;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Game {	
	
	public enum KeyType{
		Pressed, Released;
	}
	
	private World world;
	
	public void draw(Graphics2D g) {
		//The map is drawing the car too
		world.map.draw(g);
		world.pointMap.draw(g);
	}
	
	public void update(int deltaTime){
		world.car.update(deltaTime);
	}
	
	public void keyEvent(KeyEvent e, KeyType type){
		world.car.keyEvent(e, type);
	}

}
