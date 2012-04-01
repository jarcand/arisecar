package networking;

import robot.VehicleModel;


public class VehicleUpdate {
	
	public final double posX;
	public final double posY;
	public final double radius;
	public final double angle;
	
	public VehicleUpdate(VehicleModel model) {
		posX = model.getPosX();
		posY = model.getPosY();
		radius = model.getRadius();
		angle = model.getAngle();
	}
	
}
