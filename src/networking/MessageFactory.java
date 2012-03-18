package networking;

import robot.VehicleModel;

public class MessageFactory {
	
	public static final int Unknown = 0;
	public static final int XboxMovement = 1;
	public static final int KeyboardMovement = 2;
	public static final int VehicleUpdate = 3;

	public static Message createXboxMovement(XboxMovement movement, String robotName){
		Message message = new Message(XboxMovement, robotName, Message.ToRobot, Message.FromClient);
		message.setValue(movement.getX(), "x");
		message.setValue(movement.getY(), "y");
		message.setValue(movement.getZ(), "z");
		message.setValue(movement.getRX(), "rx");
		return message;
	}
	
	public static Message createKeyboardMovement(KeyboardMovement movement, String robotName) {
		Message message = new Message(KeyboardMovement, robotName, Message.ToRobot, Message.FromClient);
		message.setValue(movement.getType(), "type");
		return message;
	}
	
	public static Message createVehicleUpdate(String robotName, VehicleModel v) {
		Message msg = new Message(VehicleUpdate, robotName, Message.ToClient, Message.FromRobot);
		msg.setValue(v.getPosX(), "posX");
		msg.setValue(v.getPosY(), "posY");
		msg.setValue(v.getAngle(), "angle");
		msg.setValue(v.getRadius(), "radius");
		return msg;
	}
}
