package impar.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * 
 * This class represent the real world. It is currently
 * a grid map with empty square and full square. The robot (alias)
 * the car is able to move into empty square and is block by full
 * square. 
 * 
 * The robot gather data it receive from its sensors when one sensor
 * detect a new obstacle.
 * 
 * The function of this class is to generate the world and hold
 * the data for it. It also draws the map and the car.
 * 
 * @author Marc-Andre
 *
 */
public class Map {
	
	/**
	 * A practical way to get access to everything that exist.
	 */
	private World world;
	
	/** 
	 * Represent the different value a square can take
	 */
	public enum TypeEnum{
		wall, empty;
	}
	
	/**
	 * The size of 1 square.
	 */
	public static int size = 40;
	/** 
	 * The number of square 
	 */
	public static int number = 16;
	
	/**
	 * That's the map of all our square
	 */
	TypeEnum [][] map = new TypeEnum[number][number];
	
	/**
	 * This list is used to do detection. It is built into the
	 * constructor.
	 */
	ArrayList<Rectangle> rectList = new ArrayList<Rectangle>();
	
	/**
	 * It construct the maps
	 * 
	 * @param world
	 */
	public Map(World world) {
		this.world = world;
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
	}
	
	/**
	 * Return the value at that position in the map.
	 * This is used by the car to determine where to be placed
	 * at the begin of the simulation.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public TypeEnum get(int x, int y){
		return map[x][y];
	}
	
	/**
	 * It draws the map and it draws the car after it draw
	 * the map.
	 * 
	 * @param g
	 */
	public void draw(Graphics2D g){
		g.translate(10, 10);
		g.setColor(Color.gray);
		//Draw the edge of the map
		g.fillRect(-10, -10, number*size+20, number*size+20);
		
		//Draw all the square
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
		
		world.car.draw(g);
		g.translate(-10, -10);
		
	}

}
