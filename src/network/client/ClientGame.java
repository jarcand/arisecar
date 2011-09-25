package network.client;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientGame implements Runnable{

	final int WIDTH = 500;
	final int HEIGHT = 500;
	
	JFrame frame;
	Canvas canvas;
	BufferStrategy bufferStrategy;
	
	ClientMap map;
	
	public ClientGame(String clientName){
		
		map = new ClientMap(clientName);
		
		frame = new JFrame("CLIENT - Arise car simulation (" + clientName + ")");
		
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);
		
		canvas = new Canvas();
		canvas.setBounds(0, 0, WIDTH, HEIGHT);
		
		panel.add(canvas);
		
		canvas.addMouseListener(new MouseControl());
		canvas.addKeyListener(new KeyController());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		
		canvas.requestFocus();
	}
	
	private class MouseControl extends MouseAdapter{
		
	}
	
	private class KeyController extends KeyAdapter{
		private boolean keyF = false;
		private boolean keyD = false;
		private boolean keyJ = false;
		private boolean keyK = false;
		
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_F && keyF){
				return;
			}
			if(e.getKeyCode() == KeyEvent.VK_D && keyD){
				return;
			}
			if(e.getKeyCode() == KeyEvent.VK_J && keyJ){
				return;
			}
			if(e.getKeyCode() == KeyEvent.VK_K && keyK){
				return;
			}
			if(e.getKeyCode() == KeyEvent.VK_F){
				keyF = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				keyD = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_J){
				keyJ = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_K){
				keyK = true;
			}
			
			map.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_F){
				keyF = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				keyD = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_J){
				keyJ = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_K){
				keyK = false;
			}
			map.keyReleased(e);
		}
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
		map.render(g);
	}

	protected void update(int deltaTime) {
		map.update(deltaTime);
	}
	
	public static void main(String [] args){
		ClientGame ex = new ClientGame("Gudra");
		new Thread(ex).start();
		
		//ClientGame ex2 = new ClientGame("Kalle");
		//new Thread(ex2).start();
	}
}
