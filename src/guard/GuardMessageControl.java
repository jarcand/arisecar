package guard;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import networking.Message;
import networking.MessageFactory;

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
		if (message.getID() == MessageFactory.VehicleUpdate) {
			double x = message.get(Double.class, "posX");
			double y = message.get(Double.class, "posY");
			Point2D.Double newPoint = new Point2D.Double(x, y);
			points.add(newPoint);
			currPoint = newPoint;
			lastRadius = message.get(Double.class, "radius");
			lastAngle = message.get(Double.class, "angle");
			if (guard.getControl() != null) {
				guard.getControl().frame.repaint();
			}
		}
	}

}
