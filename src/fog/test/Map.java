package fog.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import fog.test.Game.KeyType;


public class Map {
	
	Car car;
	
	public enum TypeEnum{
		wall, empty;
	}
	
	public static int size = 40;
	public static int number = 16;
	
	TypeEnum [][] map = new TypeEnum[number][number];
	
	ArrayList<Rectangle> rectList = new ArrayList<Rectangle>();
	
	public Map(Game game) {
		for(int i=0; i<number; i++){
			for(int j=0; j<number; j++){
				if(Math.random() < 0.30){
					map[i][j] = TypeEnum.wall;
					Rectangle rect = new Rectangle(i*size, j*size, size, size);
					rectList.add(rect);
				}else{
					map[i][j] = TypeEnum.empty;
				}
			}
		}
		
		car = new Car(this, game.pointMap);
	}
	
	public TypeEnum get(int x, int y){
		return map[x][y];
	}
	
	public void draw(Graphics2D g){
		g.translate(10, 10);
		g.setColor(Color.gray);
		g.fillRect(-10, -10, number*size+20, number*size+20);
		for(int i=0; i<number; i++){
			for(int j=0; j<number; j++){
				if(map[i][j] == TypeEnum.wall){
					g.setColor(Color.black);
				}else{
					g.setColor(Color.white);
				}
				g.fillRect(i*size, j*size, size, size);
			}
		}
		
		car.draw(g);
		g.translate(-10, -10);
		
	}
	
	public void update(int deltaTime){
		car.update(deltaTime);
	}
	
	public void keyEvent(KeyEvent e, KeyType type){
		car.keyEvent(e, type);
	}

}
