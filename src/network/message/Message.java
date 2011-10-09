package network.message;

import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6242001330568255693L;

	private final int ID;
	private final boolean fromRobot;
	private final String robotName;
	protected final HashMap<String, Object> map;
	
	public Message(int ID, String robotName, boolean fromRobot){
		this.ID = ID;
		this.robotName = robotName;
		this.fromRobot = fromRobot;
		map = new HashMap<String, Object>();
	}
	
	public int getID(){
		return ID;
	}
	
	public String getRobotName(){
		return robotName;
	}
	
	public boolean isFromRobot(){
		return fromRobot;
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
