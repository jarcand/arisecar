package networking;

import java.io.Serializable;

public class KeyboardMovement implements Serializable {
	
    private static final long serialVersionUID = 8378035245359055474L;
    
	public final Direction direction;
	public final State state;
	
	public KeyboardMovement(Direction direction, State state) {
		this.direction = direction;
		this.state = state;
	}
	
	public enum Direction {
		NONE, UP, DOWN, LEFT, RIGHT
	}
	
	public enum State {
		PRESS, RELEASE
	}
}
