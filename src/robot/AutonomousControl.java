package robot;

/**
 * This class contains the algorithms necessary to give the vehicle autonomous
 * control based on the provided machine vision client.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
public class AutonomousControl {
	
	private final VehicleModel v;
	private final MVClient mv;
	
	/**
	 * Create a new autonomous control with the provided vehicle model and MV client.
	 */
	public AutonomousControl(VehicleModel v, MVClient mv) {
		this.v = v;
		this.mv = mv;
	}
	
	/**
	 * Update the vehicle model based on the machine vision information.
	 * @return Whether or not the update succeeded.
	 */
	public boolean update() {
		if (v == null || mv == null) {
			return false;
		}
		
		final float NORMAL_FORWARD_RATE = 0.6f;
		final float SLOW_FOWARD_RATE = 0.3f;
		final float STOPPED_TURN_RATE = 0.15f;
		final float MOVING_TURN_RATE = 0.2f;
		final float NO_PASS_TURN_RATE = 0.15f;
		
		Float forward;
		Float turnRate;
		
		if (!mv.isDownZoneClear()) {
			forward = 0.0f;
			
			if (mv.isLeftZoneClear() == mv.isRightZoneClear()) {
				turnRate = NO_PASS_TURN_RATE;
			} else if (!mv.isLeftZoneClear()) {
				turnRate = STOPPED_TURN_RATE;
			} else if (!mv.isRightZoneClear()) {
				turnRate = -STOPPED_TURN_RATE;
			} else {
				turnRate = 0.0f;
				Log.logFatal("FLAW in AC logic (1)");
			}
			
		} else {
			
			if (!mv.isUpZoneClear() || (!mv.isLeftZoneClear() && !mv.isRightZoneClear())) {
				forward = SLOW_FOWARD_RATE;
			} else {
				forward = NORMAL_FORWARD_RATE;
			}
			
			if (mv.isLeftZoneClear() == mv.isRightZoneClear()) {
				turnRate = 0.0f;
			} else if (!mv.isLeftZoneClear()) {
				turnRate = MOVING_TURN_RATE;
			} else if (!mv.isRightZoneClear()) {
				turnRate = -MOVING_TURN_RATE;
			} else {
				turnRate = 0.0f;
				Log.logFatal("FLAW in AC logic (2)");
			}
		}
		
		
		/*
		if(!mv.isLeftZoneClear() && !mv.isUpZoneClear() && mv.isDownZoneClear() && mv.isRightZoneClear())
		{
			forward = 0.3f;
			turnRate = 0.3f;
		}
		else if(mv.isLeftZoneClear() && !mv.isUpZoneClear() && mv.isDownZoneClear() && !mv.isRightZoneClear())
		{
			forward = 0.3f;
			turnRate = -0.3f;
		}
		else if (!mv.isUpZoneClear() && mv.isLeftZoneClear() && mv.isRightZoneClear() && mv.isDownZoneClear())
		{
			forward = 0.3f;
			turnRate = 0;
		}
		else if (!mv.isRightZoneClear() && !mv.isLeftZoneClear() && mv.isDownZoneClear() && mv.isUpZoneClear())
		{
			forward = 0.3f;
			turnRate = 0;
		}
		else if(!mv.isRightZoneClear() && !mv.isDownZoneClear() && mv.isLeftZoneClear() && mv.isUpZoneClear())
		{
			forward = 0;
			turnRate = -0.3f;
		}
		else if(mv.isRightZoneClear() && !mv.isDownZoneClear() && !mv.isLeftZoneClear() && mv.isUpZoneClear())
		{
			forward = 0;
			turnRate = 0.3f;
		}
		else if(!mv.isDownZoneClear() && mv.isLeftZoneClear() && mv.isRightZoneClear() && mv.isUpZoneClear())
		{
			forward = 0;
			turnRate = 0;
		}
		else if(mv.isDownZoneClear() && mv.isLeftZoneClear() && mv.isRightZoneClear() && mv.isUpZoneClear())
		{
			forward = 0.4f;
			turnRate = 0;
		}
		else if (!mv.isDownZoneClear() && !mv.isLeftZoneClear() && !mv.isRightZoneClear() && !mv.isUpZoneClear())
		{
			forward = 0;
			turnRate = 0.2f;
		}
		else if (mv.isDownZoneClear() && !mv.isUpZoneClear() && !mv.isLeftZoneClear() && !mv.isRightZoneClear())
		{
			forward = 0.2f;
			turnRate = 0;
		}
		else
		{
			forward = 0;
			turnRate = 0.2f;
		}
		
		/*
		if(!mv.isDownZoneClear())
		{
			if(!mv.isLeftZoneClear() && !mv.isRightZoneClear() )
			{
				forward = 0;
				turnRate = 0;
			}
			else
			{
				if(!mv.isLeftZoneClear())
				{
					forward = 0;
				    turnRate = 0.3f;
				}
				else if(!mv.isRightZoneClear())
				{
					forward = 0;
					turnRate = -0.3f;
				}
			}

		}
		else if(!mv.isUpZoneClear())
		{
			if(mv.isDownZoneClear())
			{
				if(!mv.isLeftZoneClear() && !mv.isRightZoneClear())
				{
					forward = 0.3f;
    				turnRate = 0;
				}
				else if(mv.isLeftZoneClear() && mv.isRightZoneClear())
				{
					forward = 0.3f;
					turnRate = 0;
				}
				else if(mv.isRightZoneClear())
				{
					forward = 0.3f;
					turnRate = 0.3f;
				}
				else if(mv.isLeftZoneClear())
				{
					forward = 0.3f;
					turnRate = -0.3f;
				}
			}
			else if(!mv.isDownZoneClear())
			{
				if(!mv.isLeftZoneClear() && !mv.isRightZoneClear())
				{
					forward = 0;
					turnRate = 0;
				}
				else if(mv.isLeftZoneClear())
				{
					forward = 0;
					turnRate = -0.3f;
				}
				else if(mv.isRightZoneClear())
				{
					forward = 0;
					turnRate = 0.3f;
				}
			}
			
		}
		else
		{
			forward = 0.3f;
			turnRate = 0;
		}*/
		
		
		/*if(!mv.isDownZoneClear()){
			turnRate = 0;
			System.out.println("Bottom don't move");
		}else if(!mv.isLeftZoneClear() && !mv.isRightZoneClear()){
			turnRate = 0;
			System.out.println("Can't move");
		}else if(!mv.isLeftZoneClear()){
			turnRate = 0.3f;
			System.out.println("In left zone");
		}else if(!mv.isRightZoneClear()){
			turnRate = -0.3f;
			System.out.println("In right zone");
		}*/
		
		// Update the speed and yaw rates
		v.setSpeed(forward);
		v.setYawRate(turnRate);
		
		return true;
	}
}
