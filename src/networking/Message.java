package networking;

import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = -6242001330568255693L;
	
	public static final int ToClient = 0;
	public static final int ToRobot = 1;
	public static final int ToRobotAndClient = 2;
	
	public static final int FromClient = 0;
	public static final int FromRobot = 1;

	private final int destination;
	private final int source;
	private final String robotName;
	private final Object value;
	
	public Message(String robotName, Object value, int destination, int source){
		this.robotName = robotName;
		this.value = value;
		this.destination = destination;
		this.source = source;
	}
	
	public String getRobotName(){
		return robotName;
	}
	
	public boolean isFromRobot(){
		return (source == FromRobot);
	}
	
	public boolean isFromClient(){
		return (source == FromClient);
	}
	
	public boolean isToRobot(){
		return ((destination == ToRobot) || (destination == ToRobotAndClient));
	}
	
	public boolean isToClient(){
		return ((destination == ToClient) || (destination == ToRobotAndClient));
	}
	
	public Object getValue(){
		return value;
	}
	
	@SuppressWarnings("unchecked")
	public <E extends Object>E get(Class<E> type){
		return (E) value;
	}
 
	
	@Override
	public String toString(){
		String result = "";
		if(value!=null){
			result = value.toString();
		}else{
			result = null;
		}
		return result;
	}
	
}
