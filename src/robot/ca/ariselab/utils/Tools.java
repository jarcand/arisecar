/**
 * Project:     ARISE AUV
 * Authors:     Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 * File:        utils/Tools.java
 * Date:        Fri 2011-01-07
 */

package ca.ariselab.utils;

/**
 * A collection of helper tools.
 */
public final class Tools {
	
	/** Do not allow instanciation of the class. */
	private Tools() {}
	
	/**
	 * Enfore the bounds of the axis value.
	 * @param axis - The axis value to convert.
	 * @return - The corrected axis value.
	 */
	public static float axisBounds(double axis) {
		return (float) Math.min(Math.max(axis, -1), 1);
	}
	
	/**
	 * Convert the axis value into a PWM value.
	 * @param axisInput - The axis value to convert.
	 * @return - The PWM value.
	 */
	public static int axisToPWM(float axisInput) {
		return axisToPWM(axisInput, false);
	}
	
	/**
	 * Convert the provided axis value into a pwm value and take reversing into
	 * consideration.
	 * @param axisInput - The axis value to convert.
	 * @param reversed - Whether or not the channel is reversed.
	 * @return - The PWM value.
	 */
	public static int axisToPWM(float axisInput, boolean reversed) {
		float aligned = reversed ? -axisInput : axisInput;
		int ret = Math.round((1 + aligned) / 2 * 180);
		int gamma = 5;
		if (90 - gamma < ret && ret < 90 + gamma)
			ret = 90;
		return ret;
	}
	
	/**
	 * Convert the provided short integer into a pair of bytes.
	 * @param input - The short integer to convert.
	 * @return - The pair of bytes.
	 */
	public static byte[] shortToBytes(short input) {
		byte[] ret = new byte[2];
		ret[0] = (byte) ((input >> 8) & 0xFF);
		ret[1] = (byte) ((input >> 0) & 0xFF);
		return ret;
	}
}

