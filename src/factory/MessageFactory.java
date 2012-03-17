package factory;

import factory.message.KeyboardMovement;
import factory.message.XboxMovement;
import server.Message;

public class MessageFactory {
	
	public static final int Unknown = 0;
	public static final int XboxMovement = 1;
	public static final int KeyboardMovement = 2;

	public static Message createMessage(Object obj, String robotName, int destination, int source){
		if(obj instanceof XboxMovement){
			return createMovement((XboxMovement)obj, robotName, destination, source);
		}if(obj instanceof KeyboardMovement){
			return createMovementKey((KeyboardMovement)obj, robotName, destination, source);
		}
		Message message = new Message(Unknown, robotName, destination, source);
		message.setValue(obj.toString(), "obj");
		return message;
	}

	private static Message createMovement(XboxMovement movement, String robotName, int destination, int source){
		Message message = new Message(XboxMovement, robotName, destination, source);

		message.setValue(movement.getX(), "x");
		message.setValue(movement.getY(), "y");
		message.setValue(movement.getZ(), "z");
		message.setValue(movement.getRX(), "rx");

		return message;
	}
	
	private static Message createMovementKey(KeyboardMovement movement, String robotName, int destination, int source){
		Message message = new Message(KeyboardMovement, robotName, destination, source);

		message.setValue(movement.getType(), "type");

		return message;
	}

}
