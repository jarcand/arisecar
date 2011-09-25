package network.message;

public class Message {
	
	private final String user;
	private final Object obj;
	
	public Message(String user, Object obj){
		this.user = user;
		this.obj = obj;
	}
	
	public String getUser(){
		return user;
	}
	
	public Object getObject(){
		return obj;
	}

}
