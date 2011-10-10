package user;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class InfoGUI implements Runnable{
	
	final int WIDTH = 500;
	final int HEIGHT = 500;
	
	private final ControlCenter controlCenter;
	
	private final JFrame frame;
	private final Canvas canvas;
	private final BufferStrategy bufferStrategy;
	
	public InfoGUI(){
		controlCenter = new ControlCenter();
		
		frame = new JFrame("Control Game - Arise Car");
		
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);
		
		canvas = new Canvas();
		canvas.setBounds(0, 0, WIDTH, HEIGHT);
		
		panel.add(canvas);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		
		canvas.requestFocus();
	}
	
	long desiredFPS = 100;
    long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
    
    long desiredDeltaUpdate = 10;
    
	boolean running = true;
	
	public void run(){
		
		long beginLoopTime;
		long endLoopTime;
		long currentUpdateTime = System.nanoTime();
		long lastUpdateTime;
		long deltaLoop;
		
		while(running){
			beginLoopTime = System.nanoTime();
			
			render();
			
			lastUpdateTime = currentUpdateTime;
			currentUpdateTime = System.nanoTime();
			update((int) ((currentUpdateTime - lastUpdateTime)/(1000*1000)));
			
			endLoopTime = System.nanoTime();
			deltaLoop = endLoopTime - beginLoopTime;
	        
	        if(deltaLoop > desiredDeltaLoop){
	            //Do nothing. We are already late
	        }else{
	            try{
	                Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
	            }catch(InterruptedException e){
	                //Do nothing
	            }
	        }
		}
	}
	
	protected void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		render(g);
		g.dispose();
		bufferStrategy.show();
	}
	
	protected void render(Graphics2D g){
		
	}

	protected void update(int deltaTime) {
		controlCenter.update(deltaTime);
	}
	
	public static void main(String [] args){
		InfoGUI ex = new InfoGUI();
		new Thread(ex).start();
	}

}
