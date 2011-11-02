package server;

import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6242001330568255693L;
	
	public static final int ToClient = 0;
	public static final int ToRobot = 1;
	public static final int ToRobotAndClient = 2;

	private final int ID;
	private final int destination;
	private final String robotName;
	protected final HashMap<String, Object> map;
	
	public Message(int ID, String robotName, int destination){
		this.ID = ID;
		this.robotName = robotName;
		this.destination = destination;
		map = new HashMap<String, Object>();
	}
	
	public int getID(){
		return ID;
	}
	
	public String getRobotName(){
		return robotName;
	}
	
	public boolean isToRobot(){
		return ((destination == ToRobot) || (destination == ToRobotAndClient));
	}
	
	public boolean isToClient(){
		return ((destination == ToClient) || (destination == ToRobotAndClient));
	}
	
	public void setValue(Object obj, String valueName){
		map.put(valueName, obj);
	}
	
	@SuppressWarnings("unchecked")
	public <E extends Object>E get(Class<E> type, String parameterName){
		return (E)map.get(parameterName);
	}
	
	@Override
	public String toString(){
		return "Message : " + ID;
	}

}
