/**
 * Project:     ARISE UAR
 * Authors:     Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 * File:        utils/LoopingThread.java
 * Date:        Mon 2010-07-19
 */

package ca.ariselab.utils;

/**
 * A construct to have and control a repeating method as a seperate thread.
 */
public abstract class LoopingThread extends Thread {
	private boolean running;
	private int startDelay;
	private int loopDelay;
	
	/**
	 * Construct a new looping thread.
	 * @param name - The name of the thread.
	 * @param startDelay - The delay the thread waits before starting.
	 * @param loopDelay - The delay the thread waits between executing the
	 * main loop method.
	 */
	public LoopingThread(String name, int startDelay, int loopDelay) {
		super(name);
		this.startDelay = startDelay;
		this.loopDelay = loopDelay;
	}
	
	/**
	 * Start the main looping method.
	 */
	public final void run() {
		try {
			Thread.sleep(startDelay);
		} catch (InterruptedException e) {
		}
		running = true;
		mainLoop();
		while (running) {
			try {
				Thread.sleep(loopDelay);
				mainLoop();
			} catch (InterruptedException e) {
			}
		}
	}
	
	/**
	 * Whether the looping thread is running or not.
	 * @return - Whether the thread is running or not.
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Stop the looping thread.
	 */
	public void stopLoopThread() {
		running = false;
	}
	
	/** The main loop that is executed over and over again. */
	protected abstract void mainLoop();
}

