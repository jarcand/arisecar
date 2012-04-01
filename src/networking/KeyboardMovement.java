package networking;

public class KeyboardMovement {
	
	public static final int None = -1;
	public static final int Up = 0;
	public static final int Down = 1;
	public static final int Left = 2;
	public static final int Right = 3;
	
	public static final int PRESS = 1;
	public static final int RELEASE = 2;
	
	public final int type;
	public final int state;
	
	public KeyboardMovement(int type, int state) {
		this.type = type;
		this.state = state;
	}
	

}
