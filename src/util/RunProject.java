package util;

import java.io.IOException;

public class RunProject {
	
	public static void main(String[] args){
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("cmd.exe pause");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
