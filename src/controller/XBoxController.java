package controller;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class XBoxController {
	
	public static final float cross_west = 1;
	public static final float cross_east = 0.5f;
	public static final float cross_north = 0.25f;
	public static final float cross_south = 0.75f;
	public static final float cross_northEast = 0.375f;
	public static final float cross_northWest = 0.125f;
	public static final float cross_southEast = 0.625f;
	public static final float cross_southWest = 0.875f;
	
	Controller xbox;
	
	public Component a;
	public Component b;
	public Component x;
	public Component y;
	public Component lb;
	public Component rb;
	public Component back;
	public Component start;
	public Component left;
	public Component right;
	public Component leftXAxis;
	public Component leftYAxis;
	public Component rightXAxis;
	public Component rightYAxis;
	public Component zAxis;
	public Component hat;
	
	
	public XBoxController(){
		Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for(Controller con : ca){
			if(con.getName().contains("XBOX")){
				xbox = con;
			}
		}
		System.out.println(xbox.getName());
		for(Component com : xbox.getComponents()){
			if(com.getName().contains("Button 0") || com.getName().contains("Bouton 0")){
				a = com;
			}
			if(com.getName().contains("Button 1") || com.getName().contains("Bouton 1")){
				b = com;
			}
			if(com.getName().contains("Button 2") || com.getName().contains("Bouton 2")){
				x = com;
			}
			if(com.getName().contains("Button 3") || com.getName().contains("Bouton 3")){
				y = com;
			}
			if(com.getName().contains("Button 4") || com.getName().contains("Bouton 4")){
				lb = com;
			}
			if(com.getName().contains("Button 5") || com.getName().contains("Bouton 5")){
				rb = com;
			}
			if(com.getName().contains("Button 6") || com.getName().contains("Bouton 6")){
				back = com;
			}
			if(com.getName().contains("Button 7") || com.getName().contains("Bouton 7")){
				start = com;
			}
			if(com.getName().contains("Button 8") || com.getName().contains("Bouton 8")){
				left = com;
			}
			if(com.getName().contains("Button 9") || com.getName().contains("Bouton 9")){
				right = com;
			}
			if(com.getName().contains("X Axis") || com.getName().contains("Axe X")){
				leftXAxis = com;
			}
			if(com.getName().contains("Y Axis") || com.getName().contains("Axe Y")){
				leftYAxis = com;
			}
			if(com.getName().contains("X Rotation") || com.getName().contains("Rotation X")){
				rightXAxis = com;
			}
			if(com.getName().contains("Y Rotation") || com.getName().contains("Rotation Y")){
				rightYAxis = com;
			}
			if(com.getName().contains("Z Axis") || com.getName().contains("Axe Z")){
				zAxis = com;
			}
			if(com.getName().contains("Hat") || com.getName().contains("Commande de pouce")){
				hat = com;
			}
		}
	}
	
	public void poll(){
		xbox.poll();
	}
	
	public void run(){
		while(true){
			poll();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String [] args){
		new XBoxController().run();
	}

}
