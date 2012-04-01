package ca.ariselab.utils;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JFrame;

/**
 * A debouncer for a boolean value.
 * @author Jeffrey Arcand <jeffrey.arcand@ariselab.ca>
 */
public class BoolDebounceTester {
	
	/**
	 * A stand-alone test for the BoolDebounce class.
	 */
	public static void main(String[] args) throws IOException {
		
		final BoolDebounce b = new BoolDebounce(false, 200);
		
		final Box lastTime = new BoolDebounceTester().new Box();
		lastTime.val = System.currentTimeMillis();
		
		final JFrame f = new JFrame();
		f.setSize(new Dimension(1000, 1000));
		f.setVisible(true);
		f.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) {
				lastTime.val = System.currentTimeMillis();
			}
			public void keyReleased(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}
		});
		
		(new LoopingThread("test", 0, 40) {
			public void mainLoop() {
				long curTime = System.currentTimeMillis();
				boolean val = curTime - lastTime.val < 80;
				System.out.print(val ? "  TT " : "FF   ");
				b.set(val);
				f.setTitle("" + b.get());
			}
		}).start();
	}
	
	class Box {
		long val;
	}
}
