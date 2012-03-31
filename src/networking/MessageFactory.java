package networking;

import robot.VehicleModel;

public class MessageFactory {
	
	public static Message createXboxMovement(XboxMovement movement, String robotName){
		Message message = new Message(Message.Type.XBOX_MOVEMENT, robotName, Message.ToRobot, Message.FromClient);
		message.setValue(movement.getX(), "x");
		message.setValue(movement.getY(), "y");
		message.setValue(movement.getZ(), "z");
		message.setValue(movement.getRX(), "rx");
		return message;
	}
	
	public static Message createKeyboardMovement(KeyboardMovement movement, String robotName) {
		Message message = new Message(Message.Type.KEYBOARD_MOVEMENT, robotName, Message.ToRobot, Message.FromClient);
		message.setValue(movement.getType(), "type");
		message.setValue(movement.getState(), "state");
		return message;
	}
	
	public static Message createVehicleUpdate(String robotName, VehicleModel v) {
		Message msg = new Message(Message.Type.VEHICLE_UPDATE, robotName, Message.ToClient, Message.FromRobot);
		msg.setValue(v.getPosX(), "posX");
		msg.setValue(v.getPosY(), "posY");
		msg.setValue(v.getAngle(), "angle");
		msg.setValue(v.getRadius(), "radius");
		return msg;
	}
}
