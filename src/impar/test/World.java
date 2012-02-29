package impar.test;

import impar.vision.PointMap;

public class World {
	
	public PointMap pointMap;
	public Map map;
	public Car car;
	
	public World(){
		pointMap = new PointMap();
		map = new Map(this);
		car = new Car(map, pointMap);
	}

}
