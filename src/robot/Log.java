package robot;

/**
 * A place to hook-in logging to file.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
public class Log {
	
	/**
	 * Log the provided message as an error.
	 * @param msg
	 */
	public static void logError(String msg){
		System.err.println("ERROR: " + msg);
	}
	
	/**
	 * Log the provided message as a fatal error.
	 * @param msg
	 */
	public static void logFatal(String msg){
		System.err.println("FATAL: " + msg);
	}
	
	/**
	 * Log the provided message as informational.
	 * @param msg
	 */
	public static void logInfo(String msg){
		System.out.println("INFO: " + msg);
	}
}
