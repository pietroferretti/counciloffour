package it.polimi.ingsw.ps14.message.fromserver;

public abstract class PrivateMessage {

	private Integer playerID;
	
	public PrivateMessage(Integer playerID){
	this.playerID=playerID;
	}
	
	
	public Integer getPlayerID(){
		return playerID;
	}
}