package it.polimi.ingsw.ps14.message.fromClient;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.actions.TurnAction;

public class TurnActionMsg implements Message {
	
	private TurnAction action;
	
	public TurnActionMsg(TurnAction action){
		this.action = action;
	}
	
	public TurnAction getAction() {
		return action;
	}
}
