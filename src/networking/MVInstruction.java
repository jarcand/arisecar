package networking;

import java.io.Serializable;


public class MVInstruction implements Serializable {
	
    private static final long serialVersionUID = 4736491948605932461L;
    
	public final boolean mvOn;
	
	public MVInstruction(boolean value) {
		mvOn = value;
	}
	
}
