/*
 * Project:     ARISE Serial Device Library
 * Authors:     Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 * File:        ca/ariselab/lib/serialdevices/SerialLoopingThread.java
 * Date:        Wed 2011-04-27
 * License:     GNU General Public License version 2
 */

package ca.ariselab.lib.serialdevices;

/**
 * A thread that continously executes (looping) the same method until it is
 * interrupted.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
abstract class SerialLoopingThread extends Thread {
	
	/** Whether the loop should continue looping. */
	private boolean running;
	
	/** The delay to wait before starting the looping. */
	private int startDelay;
	
	/** The delay to wait between each loop interations. */
	private int loopDelay;
	
	/**
	 * Construct a new looping thread.
	 * @param name - The name of the thread.
	 * @param startDelay - The delay to wait before starting the looping.
	 * @param loopDelay - The delay to wait between each loop interations.
	 */
	public SerialLoopingThread(String name, int startDelay, int loopDelay) {
		super(name);
		if (startDelay < 0) {
			throw new IllegalArgumentException(
			  "The provided start delay is invalid: " + startDelay);
		} else if (loopDelay < 0) {
			throw new IllegalArgumentException(
			  "The provided loop delay is invalid: " + loopDelay);
		}
		this.startDelay = startDelay;
		this.loopDelay = loopDelay;
	}
	
	/**
	 * Start the main looping method.
	 */
	public final void run() {
		try {
			running = true;
			Thread.sleep(startDelay);
			while (running) {
				mainLoop();
				Thread.sleep(loopDelay);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
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

