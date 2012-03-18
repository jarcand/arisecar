package guard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import networking.KeyboardMovement;


public class GuardControl {
	
	public final JFrame frame;
	private final Guard guard;
	
	public GuardControl(Guard guard){
		this.guard = guard;
		
		frame = new JFrame("GuardControl") {
            private static final long serialVersionUID = -9188506391582322204L;
			public void paint(Graphics g) {
				super.paint(g);
				paintTrail(g);
				paintVehicle(g);
			}
		};

		frame.addKeyListener(new KeyboardControl());
		
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
	
	private class KeyboardControl implements KeyListener {

		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;
		
		private void setAllFalse(){
			up = false;
			down = false;
			left = false;
			right = false;
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("pressed: " + e.getKeyCode());
			if(e.getKeyCode() == KeyEvent.VK_UP){
				System.out.println("up key");
				if(!up){
					setAllFalse();
					up = true;
					//Create message up and send it
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.Up);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
				System.out.println("down key");
				if(!down){
					setAllFalse();
					down = true;
					//Create message down and send it
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.Down);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
				System.out.println("left key");
				if(!left){
					setAllFalse();
					left = true;
					//Create message left and send it
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.Left);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				System.out.println("right key");
				if(!right){
					setAllFalse();
					right = true;
					//Create message right and send it
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.Right);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("released: " + e.getKeyCode());
			if(e.getKeyCode() == KeyEvent.VK_UP){
				if(up){
					setAllFalse();
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.None);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
				if(down){
					setAllFalse();
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.None);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
				if(left){
					setAllFalse();
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.None);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				if(right){
					setAllFalse();
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.None);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
		
	}
	
	private void paintTrail(Graphics g) {
		int offsetX = 300;
		int offsetY = 300;
		Point2D.Double lastPoint = null;
		g.setColor(Color.ORANGE);
		for (Point2D.Double point : guard.getMessageControl().points) {
			if (lastPoint != null) {
				g.drawLine(offsetX + (int) lastPoint.getX(), offsetY + (int) lastPoint.getY(), offsetX + (int) point.getX(), offsetY + (int) point.getY());
			}
			lastPoint = point;
		}
	}
	
	private void paintVehicle(Graphics g) {
		//TODO
		System.out.println("paintVehicle");
	}
}
