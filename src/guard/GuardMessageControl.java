package guard;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import networking.Message;

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
		if (message.getID() == Message.Type.VEHICLE_UPDATE) {
			lastRadius = message.get(Double.class, "radius");
			lastAngle = message.get(Double.class, "angle");
			
			double x = message.get(Double.class, "posX");
			double y = message.get(Double.class, "posY");
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
		} else if (message.getID() == Message.Type.MV_UPDATE) {
			if (guard.getGUI() != null) {
				guard.getGUI().updateZones(message);
			}
		}
	}

}
