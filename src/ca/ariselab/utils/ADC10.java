package ca.ariselab.utils;

public final class ADC10 {
	private ADC10() {}
	
	/**
	 * Convert a 10-bit ADC reading of an ASC756 SCA-100B to its current reading.
	 * @param adc10
	 * @return
	 */
	public static float convertASC756SCA100B(int adc10) {
		return ((adc10 - 510) * 5 / 1024f) / 0.020f;
	}

	/**
	 * Convert a 10-bit ADC reading of an MCP7700A to its thermal reading.
	 * @param adc10
	 * @return
	 */
	public static float convertMCP9700A(int adc10) {
		return ((adc10 * 5 / 1024f) - 0.500f) / 0.010f;
	}
}
