package server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * 
 * This class save every message the server receive in a file (Log.txt).
 * 
 * The file is usually located in the root of the project in a folder called
 * logging at the same level as the bin folder.
 * 
 * @author Marc-Andre
 *
 */
public class GeneralLog {
	
	String pathName = "../logging/Log.txt";
	String pathName2 = "logging/Log.txt";
	File file;
	
	boolean printOut = true;
	
	public GeneralLog(){
		File dir = new File(".");
		String parent = dir.getAbsolutePath();
		if(parent.contains("bin")){
			file = new File(pathName);
		}else{
			file = new File(pathName2);
		}
	}
	
	public GeneralLog(String path){
		file = new File(path);
	}
	
	private void log(Object obj){
		if(printOut){
			System.out.println("- " + obj);
		}
	}
	
	public String getTime(){
		String result = "[";
		Calendar cal = Calendar.getInstance();
		result += cal.getTime().toString();
		result += "]";
		return result;
	}
	
	public void messageFromClient(String message, String clientName){
		String result = getTime();
		result += "[guard="+clientName+"]";
		result += "[message] :";
		result += message;
		writeMessage(result);
	}
	
	public void messageFromRobot(String message, String robotName){
		String result = getTime();
		result += "[robot="+robotName+"]";
		result += "[message] :";
		result += message;
		writeMessage(result);
	}
	
	public void statusFromClient(String message, String clientName){
		String result = getTime();
		result += "[client="+clientName+"]";
		result += "[status] :";
		result += message;
		writeMessage(result);
	}
	
	public void statusFromRobot(String message, String robotName){
		String result = getTime();
		result += "[robot="+robotName+"]";
		result += "[status] :";
		result += message;
		writeMessage(result);
	}
	
	public void statusFromServer(String message){
		String result = getTime();
		result += "[server]";
		result += "[status] :";
		result += message;
		writeMessage(result);
	}
	
	public void writeMessage(String s){
		log(s);
		FileWriter writer;
		try {
			writer = new FileWriter(file, true);
			writer.append("- ");
			writer.append(s);
			writer.append("\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public static void main(String [] args){
		GeneralLog log = new GeneralLog();
		log.writeMessage("Test of logging file");
	}
	
}
