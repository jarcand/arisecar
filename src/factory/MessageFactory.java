package factory;

import factory.message.XboxMovement;
import server.Message;

public class MessageFactory {
	
	public static final int XboxMovement = 0;

	public static Message createMessage(Object obj, String robotName, int destination){
		if(obj instanceof XboxMovement){
			return createMovement((XboxMovement)obj, robotName, destination);
		}
		return null;
	}

	private static Message createMovement(XboxMovement movement, String robotName, int destination){
		Message message = new Message(XboxMovement, robotName, destination);

		message.setValue(movement.getX(), "x");
		message.setValue(movement.getY(), "y");
		message.setValue(movement.getZ(), "z");
		message.setValue(movement.getRX(), "rx");

		return message;
	}

}
