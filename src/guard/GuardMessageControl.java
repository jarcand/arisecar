package guard;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import networking.MVUpdate;
import networking.Message;
import networking.VehicleUpdate;

public class GuardMessageControl {
	
	private final Guard guard;
	public ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
	public Point2D.Double currPoint = new Point2D.Double();
	public double lastRadius = 30;
	public double lastAngle = 0;
	
	public GuardMessageControl(Guard guard) {
		this.guard = guard;
	}
	
	public void handleMessage(Message message){
		if (message.getValue() instanceof VehicleUpdate) {
			VehicleUpdate vh = message.get(VehicleUpdate.class);
			lastRadius = vh.radius;
			lastAngle = vh.angle;
			
			double x = vh.posX;
			double y = vh.posY;
			
			if (points.size() > 0) {
				Point2D.Double lastPoint = points.get(points.size() - 1);
				if (lastPoint.getX() == x && lastPoint.getY() == y) {
					return;
				}
			}
			Point2D.Double newPoint = new Point2D.Double(x, y);
			points.add(newPoint);
			currPoint = newPoint;
			if (guard.getGUI() != null) {
				guard.getGUI().frame.repaint();
			}
		} else if (message.getValue() instanceof MVUpdate) {
			if (guard.getGUI() != null) {
				guard.getGUI().updateZones(message.get(MVUpdate.class));
			}
		}
	}

}
