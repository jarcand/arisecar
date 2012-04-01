package ca.ariselab.utils;

/**
 * A debouncer for a boolean value.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
public class BoolDebounce {
	
	private final int bounceLength;
	private boolean curValue;
	private boolean lastUpdateValue;
	private long lastUpdate = 0;
	
	/**
	 * Create a new debouncer with the provided properties.
	 * @param initValue The initial value of the boolean.
	 * @param bounceLength The required stable-signal length to change the value.
	 */
	public BoolDebounce(boolean initValue, int bounceLength) {
		curValue = initValue;
		lastUpdateValue = curValue;
		this.bounceLength = bounceLength;
	}
	
	/**
	 * @return The value of the debounced boolean.
	 */
	public boolean get() {
		return curValue;
	}
	
	/**
	 * Try to set the value of the boolean.
	 * @param newValue The target value, on which debouncing is applied.
	 */
	public void set(boolean newValue) {
		long curTime = System.nanoTime();
		
		// If the value changed, record the transition time
		if (newValue != lastUpdateValue) {
			lastUpdate = curTime;
		}
		lastUpdateValue = newValue;
		
		// Calculate how long since the value has transitioned
		long dTime = (curTime - lastUpdate) / 1000 / 1000;
		
		// If the new value is not the same as the current value,
		// and it's been long enough since it transitioned, change it
		if (newValue != curValue && dTime > bounceLength) {
			curValue = newValue;
		}
	}
}
