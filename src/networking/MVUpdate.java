package networking;

import java.io.Serializable;
import robot.MVClient;


public class MVUpdate implements Serializable {
	
    private static final long serialVersionUID = -4152493846393189616L;
    
	public final boolean down;
	public final boolean up;
	public final boolean left;
	public final boolean right;
	
	public MVUpdate(MVClient mv) {
		down = mv.isDownZoneClear();
		up = mv.isUpZoneClear();
		left = mv.isLeftZoneClear();
		right = mv.isRightZoneClear();
	}
	
}
