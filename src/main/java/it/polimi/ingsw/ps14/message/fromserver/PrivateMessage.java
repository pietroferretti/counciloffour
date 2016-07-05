package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;

public abstract class PrivateMessage implements Message {

	private Integer playerID;
	
	public PrivateMessage() {
		playerID = -1;
	}
	
	public PrivateMessage(Integer playerID){
	this.playerID=playerID;
	}
	
	
	public Integer getPlayerID(){
		return playerID;
	}
}
