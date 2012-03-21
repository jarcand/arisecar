package guard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import networking.KeyboardMovement;


public class GuardControl {
	
	private int offsetX = 300;
	private int offsetY = 300;
	
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
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new KeyboardControl());
		
		frame.setSize(1600, 800);
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
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.Up, KeyboardMovement.PRESS);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
				System.out.println("down key");
				if(!down){
					setAllFalse();
					down = true;
					//Create message down and send it
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.Down, KeyboardMovement.PRESS);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
				System.out.println("left key");
				if(!left){
					setAllFalse();
					left = true;
					//Create message left and send it
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.Left, KeyboardMovement.PRESS);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				System.out.println("right key");
				if(!right){
					setAllFalse();
					right = true;
					//Create message right and send it
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.Right, KeyboardMovement.PRESS);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			} else {
				System.out.println("other key");
				setAllFalse();
				//Create message right and send it
				KeyboardMovement message = new KeyboardMovement(KeyboardMovement.None, KeyboardMovement.PRESS);
				guard.getClient().sendToRobot(message, Guard.DefaultName);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("released: " + e.getKeyCode());
			if(e.getKeyCode() == KeyEvent.VK_UP){
				if(up){
					setAllFalse();
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.Up, KeyboardMovement.RELEASE);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
				if(down){
					setAllFalse();
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.Down, KeyboardMovement.RELEASE);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
				if(left){
					setAllFalse();
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.Left, KeyboardMovement.RELEASE);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				if(right){
					setAllFalse();
					KeyboardMovement message = new KeyboardMovement(KeyboardMovement.Right, KeyboardMovement.RELEASE);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
		
	}
	
	private void paintTrail(Graphics g) {
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
		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(1));
			
			GuardMessageControl mc = guard.getMessageControl();
			
			g2.setColor(Color.blue);
			int x = offsetX + (int) (mc.currPoint.getX() - mc.lastRadius);
			int y = offsetY + (int) (mc.currPoint.getY() - mc.lastRadius);
			int width = (int) (mc.lastRadius * 2);
			int height = (int) (mc.lastRadius * 2);
			g2.fillOval(x, y, width, height);
			
			g2.setColor(Color.pink);
			x = offsetX + (int) (mc.currPoint.getX());
			y = offsetY + (int) (mc.currPoint.getY());
			int x2 = x + (int) (mc.lastRadius * Math.cos(mc.lastAngle));
			int y2 = y + (int) (mc.lastRadius * Math.sin(mc.lastAngle));
			g2.drawLine(x, y, x2, y2);
		}
	}
}
