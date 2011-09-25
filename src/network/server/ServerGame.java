package network.server;


import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import robot.ServerMap;

public class ServerGame implements Runnable{
	
	final int WIDTH = 500;
	final int HEIGHT = 500;
	
	JFrame frame;
	Canvas canvas;
	BufferStrategy bufferStrategy;
	
	ServerMap serverMap = new ServerMap();
	
	public ServerGame(){
		frame = new JFrame("SERVER - Arise car simulation");
		
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
		serverMap.render(g);
	}

	protected void update(int deltaTime) {
		serverMap.update(deltaTime);
	}
	
	public static void main(String [] args){
		ServerGame ex = new ServerGame();
		new Thread(ex).start();
	}

}
