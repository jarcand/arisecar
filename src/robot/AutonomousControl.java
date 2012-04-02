package robot;

import ca.ariselab.utils.BoolDebounce;

/**
 * This class contains the algorithms necessary to give the vehicle autonomous
 * control based on the provided machine vision client.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
public class AutonomousControl {
	
	private static final float NORMAL_FORWARD_RATE = 0.3f;
	private static final float SLOW_FOWARD_RATE = 0.15f;
	private static final float STOPPED_TURN_RATE = 0.07f;
	private static final float MOVING_TURN_RATE = 0.1f;
	private static final float NO_PASS_TURN_RATE = 0.07f;
	
	private static final float MAX_SPEED_CHANGE = 0.02f;
	private static final float MAX_YAW_CHANGE = 0.02f;
	
	private final VehicleModel v;
	private final MVClient mv;
	
	private BoolDebounce downZoneClear, upZoneClear, leftZoneClear, rightZoneClear;
	
	private float lastSpeed = 0;
	private float lastYaw = 0;
	
	/**
	 * Create a new autonomous control with the provided vehicle model and MV client.
	 */
	public AutonomousControl(VehicleModel v, MVClient mv) {
		this.v = v;
		this.mv = mv;
		
		downZoneClear = new BoolDebounce(false, 100);
		upZoneClear = new BoolDebounce(false, 100);
		leftZoneClear = new BoolDebounce(false, 100);
		rightZoneClear = new BoolDebounce(false, 100);
	}
	
	/**
	 * Reset the internal variables.
	 * TODO: this should not be necessary.
	 */
	public void reset() {
		lastSpeed = 0;
		lastYaw = 0;
	}
	
	/**
	 * Update the vehicle model based on the machine vision information.
	 * @return Whether or not the update succeeded.
	 */
	public boolean update() {
		if (v == null || mv == null) {
			return false;
		}
		
		downZoneClear.set(mv.isDownZoneClear());
		leftZoneClear.set(mv.isLeftZoneClear());
		rightZoneClear.set(mv.isRightZoneClear());
		upZoneClear.set(mv.isUpZoneClear());
		
		Float forward;
		Float turnRate;
		
		if (!downZoneClear.get()) {
			forward = 0.0f;
			
			if (leftZoneClear == rightZoneClear) {
				turnRate = NO_PASS_TURN_RATE;
			} else if (!leftZoneClear.get()) {
				turnRate = STOPPED_TURN_RATE;
			} else if (!rightZoneClear.get()) {
				turnRate = -STOPPED_TURN_RATE;
			} else {
				turnRate = 0.0f;
				Log.logFatal("FLAW in AC logic (1)");
			}
			
		} else {
			
			if (!upZoneClear.get()) {
				forward = 0.0f;
			} else if (!leftZoneClear.get() && !rightZoneClear.get()) {
				forward = SLOW_FOWARD_RATE;
			} else {
				forward = NORMAL_FORWARD_RATE;
			}
			
			if (leftZoneClear == rightZoneClear) {
				turnRate = 0.0f;
			} else if (!leftZoneClear.get()) {
				turnRate = MOVING_TURN_RATE;
			} else if (!rightZoneClear.get()) {
				turnRate = -MOVING_TURN_RATE;
			} else {
				turnRate = 0.0f;
				Log.logFatal("FLAW in AC logic (2)");
			}
		}
		
		float deltaSpeed = forward - lastSpeed;
		float deltaYaw = turnRate - lastYaw;
		
		if (deltaSpeed > MAX_SPEED_CHANGE && forward > 0) {
			deltaSpeed = MAX_SPEED_CHANGE;
		} else if (deltaSpeed < -MAX_SPEED_CHANGE && forward < 0) {
			deltaSpeed = -MAX_SPEED_CHANGE;
		}
		
		if (deltaYaw > MAX_YAW_CHANGE) {
			deltaYaw = MAX_YAW_CHANGE;
		} else if (deltaYaw < -MAX_YAW_CHANGE) {
			deltaYaw = -MAX_YAW_CHANGE;
		}
		
		lastSpeed += deltaSpeed;
		lastYaw += deltaYaw;
		
		// Update the speed and yaw rates
		v.setSpeed(lastSpeed);
		v.setYawRate(lastYaw);
		
		return true;
	}
}
