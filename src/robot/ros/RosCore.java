package ros;

import java.util.HashMap;

/**
 * Stupid design
 * @author Marc-Andre
 */
public class RosCore {
	
	public static final int ControlSystem = 0;
	
	private HashMap<Integer, RosNode> nodeMap = new HashMap<Integer, RosNode>();
	
	
	
	public void addNode(RosNode node, int id){
		nodeMap.put(id, node);
	}
	
	public void sendMessage(Object message, int id){
		RosNode node = nodeMap.get(id);
		node.receiveMessage(message);
	}
	
}
