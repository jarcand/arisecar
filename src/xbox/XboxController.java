package xbox;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Component.POV;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class XboxController {
	
	public final float cross_west = 1;
	public final float cross_east = 0.5f;
	public final float cross_north = 0.25f;
	public final float cross_south = 0.75f;
	public final float cross_northEast = 0.375f;
	public final float cross_northWest = 0.125f;
	public final float cross_southEast = 0.625f;
	public final float cross_southWest = 0.875f;
	
	public final float leftAnalog_west = -1;
	public final float leftAnalog_east = 1;
	public final float rightAnalog_north = -1;
	public final float rightAnalog_south = 1;
	
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
	
	
	public XboxController(){
		Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for(Controller con : ca){
			if(con.getName().toLowerCase().contains("xbox")){
				xbox = con;
			} else {
				System.out.println(con.getName());
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
