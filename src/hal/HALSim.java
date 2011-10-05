package hal;

import simulation.robot.module.RobotData;

public class HALSim implements HAL {
	
	private final RobotData robotData;
	
	public HALSim(RobotData robotData) {
		this.robotData = robotData;
	}

	@Override
	public void setLeftMotor(int thrustLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLeftMotor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRightMotor(int thrustLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRightMotor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLeftBrake(boolean active) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getLeftBrake() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRightBrake(boolean active) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getRightBrake() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMotorsRelay(boolean active) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getMotorsRelay() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getLeftRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRightRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLeftMotorCurrent() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRightMotorCurrent() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBattery1Current() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBattery1Voltage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBattery2Current() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBattery2Voltage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getEstop() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRange(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getContact(int index) {
		// TODO Auto-generated method stub
		return false;
	}

}
