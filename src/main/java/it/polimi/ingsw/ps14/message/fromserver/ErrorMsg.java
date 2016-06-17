package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;

public class ErrorMsg extends PrivateMessage implements Message {
	

	/**
	 * error message for example for invalid action
	 */
	private static final long serialVersionUID = 3045800910741012051L;
	private final String text;
	
	public ErrorMsg(Integer playerID, String text){
		super(playerID);
		this.text=text;
	}
	
	public String getInfo(){
		return text;
	}
	
}
