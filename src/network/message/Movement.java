package network.message;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Movement implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int IsPressed = 0;
	public static final int IsReleased = 1;
	
	private final int eventType;
	private final KeyEvent keyEvent;
	
	public Movement(KeyEvent e, int eventType){
		this.eventType = eventType;
		keyEvent = e;
	}
	
	public boolean isPressed(){
		return eventType == IsPressed;
	}
	
	public boolean isReleased(){
		return eventType == IsReleased;
	}
	
	public KeyEvent getKeyEvent(){
		return keyEvent;
	}

}
