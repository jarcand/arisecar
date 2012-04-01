package networking;

public class KeyboardMovement {
	
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
