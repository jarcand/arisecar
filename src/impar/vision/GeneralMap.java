package impar.vision;

import java.util.ArrayList;

/**
 * This class hold the data of all the point. The point are sort by the cell in
 * which they are recorded. For the moment there is 1 cell per pixel but at a later
 * point we might wish to reduce that number for memory reason.
 * 
 * @author Gudradain
 *
 */
public class GeneralMap {
	
	public static int MinimumValidity = 10;
	public static int MinimumCollision = 100;
	private double validityDist = 50;
	
	private ArrayList<Cell> cellList = new ArrayList<Cell>();
	private ArrayList<Point> collisionPointList = new ArrayList<Point>();
	public long time = 0; //In milliseconds
	long timeBetweenCleanUp = 100; //In milliseconds
	long timeBeforeNextCleanUp = timeBetweenCleanUp;
	long timeSurvive = 2000; //In milliseconds
	
	/**
	 * Define the angle in which we put point together. For example if we set
	 * this value to 2, point between 0 and 2 would be together and point between
	 * 2 and 4 together and so on.
	 * 
	 * This is done to regroup a couple of point together and reduce the size of
	 * the data we must keep into memory.
	 */
	private double angleGroup = 5*(2*Math.PI)/360;
	
	/**
	 * We don't really want to keep point with low validity in memory because
	 * they don't provide reliable information, so we should eliminate those
	 * point in a certain way.
	 * 
	 * The way I decide to chose for now is to remove them if they are older
	 * than 30 seconds and I do this check every second.
	 * 
	 * @param deltaTime
	 */
	public void update(int deltaTime){
		time += deltaTime;
		timeBeforeNextCleanUp -= deltaTime;
		if(timeBeforeNextCleanUp < 0){
			timeBeforeNextCleanUp = timeBetweenCleanUp;
			//Clean up the point to generate the map
			for(Cell cell : cellList){
				for(int i=0; i<cell.pointList.size(); i++){
					Point point = cell.pointList.get(i);
					if(point.validity < MinimumValidity && time-point.time > timeSurvive){
						cell.pointList.remove(i);
						i--;
					}
				}
			}
			//Clean up the point for collision detection
			for(int i=0; i<collisionPointList.size(); i++){
				Point point = collisionPointList.get(i);
				if(time-point.time > MinimumCollision){
					collisionPointList.remove(i);
					i--;
				}
			}
		}
	}
	
	public void add(Point point){
		Cell cell = getCell(point.x, point.y);
		cell.add(point);
	}
	
	public ArrayList<Point> getCollisionList(){
		return collisionPointList;
	}
	
	public ArrayList<Cell> getCellList(){
		return cellList;
	}
	
	public Cell getCell(int x, int y){
		for(Cell cell : cellList){
			if(cell.x == x && cell.y == y){
				return cell;
			}
		}
		//No cell found. Create a new one
		Cell cell = new Cell(x, y);
		cellList.add(cell);
		
		return cell;
	}
	
	public class Cell {
		private int x;
		private int y;
		private ArrayList<Point> pointList = new ArrayList<Point>();
		
		public Cell(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public ArrayList<Point> getList(){
			return pointList;
		}
		
		/**
		 * When we add a point to cell we want to validate it against the other points
		 * into that cell.
		 * 
		 * So to do that, we separate each point into a series of angle. For example,
		 * all the points that have an angle between 0 and 2 will be together. We
		 * will then calculate the approximate value from all those point and create
		 * a single point with an higher degree of validity. Each point we add to this
		 * group will increase the degree of validity and we will keep the number of point
		 * we store into memory low.
		 * 
		 * @param point
		 */
		public void add(Point point){
			//Add the point to the map generation
			int validity = 1;
			if(point.dist < validityDist){
				validity += 1;
			}
			if(point.dist < validityDist/2){
				validity += 1;
			}
			double dist = point.dist*validity;
			for(int i=0; i<pointList.size(); i++){
				Point p = pointList.get(i);
				if((int)(p.angle/angleGroup) == (int)(point.angle/angleGroup)){
					pointList.remove(i);
					validity += p.validity;
					dist += p.dist*p.validity;
				}
			}
			dist = dist/validity;
			double angle = (int)(point.angle/angleGroup);
			angle = angle * angleGroup;
			angle += angleGroup/2;
			Point newPoint = new Point(x, y, dist, angle, validity);
			newPoint.time = time;
			pointList.add(newPoint);
			
			//Add the point to the collision detection
			Point colPoint = new Point(point.x, point.y, point.dist, point.angle);
			colPoint.time = time;
			collisionPointList.add(colPoint);
		}
	}
	
	

}
