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
		
		float forward = 0;
		float turnRate = 0;
		
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
		v.setYawRate(turnRate / 2);
		
		return true;
	}
}
