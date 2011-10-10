package controller;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Component.POV;
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
	
	private Controller xbox;
	
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
			
			if(com.getIdentifier() == Button._0){ a = com; }
			else if(com.getIdentifier() == Button._1){ b = com; }
			else if(com.getIdentifier() == Button._2){ x = com; }
			else if(com.getIdentifier() == Button._3){ y = com; }
			else if(com.getIdentifier() == Button._4){ lb = com; }
			else if(com.getIdentifier() == Button._5){ rb = com; }
			else if(com.getIdentifier() == Button._6){ back = com; }
			else if(com.getIdentifier() == Button._7){ start = com; }
			else if(com.getIdentifier() == Button._8){ left = com; }
			else if(com.getIdentifier() == Button._9){ right = com; }
			
			else if(com.getIdentifier() == Axis.X){ leftXAxis = com; }
			else if(com.getIdentifier() == Axis.Y){ leftYAxis = com; }
			else if(com.getIdentifier() == Axis.RX){ rightXAxis = com; }
			else if(com.getIdentifier() == Axis.RY){ rightYAxis = com; }
			else if(com.getIdentifier() == Axis.Z){ zAxis = com; }
			
			else if(com instanceof POV){ hat = com; }
		}
	}
	
	public void poll(){
		xbox.poll();
	}

}
