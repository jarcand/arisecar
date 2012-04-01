package networking;

import robot.MVClient;


public class MVUpdate {
	
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
