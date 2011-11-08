package factory.message;

public class KeyboardMovement {
	
	public static final int None = -1;
	public static final int Up = 0;
	public static final int Down = 1;
	public static final int Left = 2;
	public static final int Right = 3;
	
	private final int type;
	
	public KeyboardMovement(int type) {
		this.type = type;
	}
	
	public int getType(){
		return type;
	}

}
