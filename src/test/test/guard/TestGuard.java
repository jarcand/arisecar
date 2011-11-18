package test.guard;

import guard.Guard;

public class TestGuard {
	
	public static void main(String [] args){
		Guard guard = new Guard();
		guard.getClient().sendToRobot("Hello", "Gudra");
	}

}
