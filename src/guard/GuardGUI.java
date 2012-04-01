package guard;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import networking.KeyboardMovement;
import networking.MVUpdate;
import networking.MessageFactory;
import networking.XboxMovement;

public class GuardGUI {
	
	private int offsetX = 300;
	private int offsetY = 300;
	
	public final JFrame frame;
	private final Guard guard;
	
	private boolean controllerEnabled = false;
	
	private JLabel zones;
	private JLabel instructions;
	
	public GuardGUI(Guard guard2) {
		this.guard = guard2;
		
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
		
		zones = new JLabel("nothing");
		instructions = new JLabel("nothing");
		final JCheckBox mvOnChk = new JCheckBox("MV On");
		
		JPanel panel = new JPanel();
		panel.add(zones);
		panel.add(instructions);
		panel.add(mvOnChk);
		
		mvOnChk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				guard.getClient().sendToServer(MessageFactory.createMVInstruction(Guard.DefaultName, mvOnChk.isSelected()));			
			}
		});
		
		frame.getContentPane().add(zones, BorderLayout.NORTH);
		
		frame.getContentPane().add(instructions, BorderLayout.SOUTH);
		
		frame.setVisible(true);
		
		XboxControl xboxListener = new XboxControl();
		xboxListener.start();
	}
	
	private class XboxControl extends Thread {
		public void run() {
			XboxController controller = null;
			try {
				controller = new XboxController();
			} catch (FileNotFoundException e1) {
				System.err.println(e1);
			}
			while (controller != null) {
				controller.poll();
				if(!controllerEnabled)
					if(controller.start.getPollData() == 1)
						controllerEnabled = true;
				if (controllerEnabled) {
					float leftAnalog = controller.leftXAxis.getPollData();
					float rightAnalog = controller.rightYAxis.getPollData();
					
					if(leftAnalog < 0.25 && leftAnalog > -0.25)
						leftAnalog = 0;
					if(rightAnalog < 0.25 && rightAnalog > -0.25)
						rightAnalog = 0;
					
					XboxMovement message = new XboxMovement(leftAnalog, rightAnalog);
					guard.getClient().sendToRobot(message, Guard.DefaultName);
					
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}
	
	public void updateZones(MVUpdate msg) {
		boolean downZoneClear = msg.down;
		boolean upZoneClear = msg.up;
		boolean leftZoneClear = msg.left;
		boolean rightZoneClear = msg.right;
		
		zones.setText("down: " + downZoneClear + ", up: " + upZoneClear + ", left: "
		+ leftZoneClear + ", right: " + rightZoneClear);
		
		String instr = "none";
		if (!downZoneClear) {
			instr = "STOP";
		} else {
			if (!leftZoneClear) {
				instr = "move right";
			} else if (!rightZoneClear) {
				instr = "move left";
			} else {
				instr = "forward";
			}
			if (!upZoneClear) {
				instr += ", SLOWLY";
			}
		}
		instructions.setText(instr);
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
			} 
			else {
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
