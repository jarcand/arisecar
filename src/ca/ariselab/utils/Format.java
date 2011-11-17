package ca.ariselab.utils;

public final class Format {
	private Format() {}
	
	private static float roundFactor(float input, float factor) {
		return Math.round(input * factor) / factor;
	}
	
	public static float round10(float input) {
		return roundFactor(input, 10);
	}

}
