
package controller;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class Test {
	
	public static void main(String [] args){
		Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();

		for(int i = 0; i < ca.length; i++){
			System.out.println("Found input device: " + ca[i].getName());
			System.out.println("-------------------------");
			for(Component con : ca[i].getComponents()){
				System.out.println("Found input device: " + con.getName());
			}
			System.out.println("============================");
		}
	}

}
