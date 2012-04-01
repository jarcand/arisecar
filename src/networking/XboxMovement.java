package networking;

import java.io.Serializable;

public class XboxMovement implements Serializable {

    private static final long serialVersionUID = 5105975081272298148L;
    
	public final float x;
	public final float y;

	public XboxMovement(float leftAnalog, float rightAnalog)
	{
		this.x = leftAnalog;
		this.y = rightAnalog;
	}
}
