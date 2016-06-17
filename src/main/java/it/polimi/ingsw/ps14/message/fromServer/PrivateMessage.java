package it.polimi.ingsw.ps14.message.fromServer;

public abstract class PrivateMessage {

	private Integer playerID;
	
	public PrivateMessage(Integer playerID){
	this.playerID=playerID;
	}
	
	
	public Integer getPlayerID(){
		return playerID;
	}
}
