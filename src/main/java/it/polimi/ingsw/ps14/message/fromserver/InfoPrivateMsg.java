package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;

public class InfoPrivateMsg extends PrivateMessage implements Message {

	private static final long serialVersionUID = 3045800910741012051L;

	private final String text;
	
	public InfoPrivateMsg(Integer playerID, String text){
		super(playerID);
		this.text=text;
	}
	
	public String getInfo(){
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
}
