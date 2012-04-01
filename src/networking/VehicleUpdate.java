package networking;

import java.io.Serializable;
import robot.VehicleModel;


public class VehicleUpdate implements Serializable {
	
    private static final long serialVersionUID = -4554689134429484514L;
    
	public final double posX;
	public final double posY;
	public final double radius;
	public final double angle;
	
	public VehicleUpdate(VehicleModel model) {
		posX = model.getPosX();
		posY = model.getPosY();
		radius = model.getRadius();
		angle = model.getHeading();
	}
	
}
