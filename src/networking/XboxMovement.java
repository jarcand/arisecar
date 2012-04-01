package networking;

public class XboxMovement {

private float leftAnalog;
private float rightAnalog;

	public XboxMovement(float leftAnalog, float rightAnalog)
	{
		this.leftAnalog = leftAnalog;
		this.rightAnalog = rightAnalog;
	}
	
	public float getLeftAnalog()
	{
		return leftAnalog;
	}
	public float getRightAnalog()
	{
		return rightAnalog;
	}

}
