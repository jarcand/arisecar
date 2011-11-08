package server;

import javax.swing.JFrame;

public class ServerWindow {
	
	private JFrame frame;
	
	public ServerWindow(){
		frame = new JFrame("Host Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setVisible(true);
		
	}

}
