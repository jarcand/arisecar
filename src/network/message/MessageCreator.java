package network.message;

public class MessageCreator {
	
	public static final int XboxMovement = 0;
	
	public static Message createMessage(Object obj, String robotName, boolean fromRobot){
		if(obj instanceof Movement){
			return createMovement((Movement)obj, robotName, fromRobot);
		}
		return null;
	}
	
	private static Message createMovement(Movement movement, String robotName, boolean fromRobot){
		Message message = new Message(XboxMovement, robotName, fromRobot);
		
		message.setValue(movement.getX(), "x");
		message.setValue(movement.getY(), "y");
		message.setValue(movement.getZ(), "z");
		message.setValue(movement.getRX(), "rx");
		
		return message;
	}

}
