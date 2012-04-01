package networking;

import robot.MVClient;
import robot.VehicleModel;

public class MessageFactory {
	
	public static Message createXboxMovement(XboxMovement movement, String robotName){
		Message message = new Message(robotName, movement, Message.ToRobot, Message.FromClient);
		return message;
	}
	
	public static Message createKeyboardMovement(KeyboardMovement movement, String robotName) {
		Message message = new Message(robotName, movement, Message.ToRobot, Message.FromClient);
		return message;
	}
	
	public static Message createMVInstruction(String robotName, boolean mvOn) {
		Message msg = new Message(robotName, new MVInstruction(mvOn), Message.ToClient, Message.FromRobot);
		return msg;
	}
	
	public static Message createMVUpdate(String robotName, MVClient mv) {
		Message msg = new Message(robotName, new MVUpdate(mv), Message.ToClient, Message.FromRobot);
		return msg;
	}
	
	public static Message createVehicleUpdate(String robotName, VehicleModel v) {
		Message msg = new Message(robotName, new VehicleUpdate(v), Message.ToClient, Message.FromRobot);
		return msg;
	}
}
