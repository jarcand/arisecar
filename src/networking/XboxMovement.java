package networking;

public class XboxMovement {

	public final float x;
	public final float y;

	public XboxMovement(float leftAnalog, float rightAnalog)
	{
		this.x = leftAnalog;
		this.y = rightAnalog;
	}
}
