package it.polimi.ingsw.ps14.message;

import it.polimi.ingsw.ps14.model.actions.Action;

public class ActionMsg implements Message {
	
	private Action action;
	
	public ActionMsg(Action action){
		this.action = action;
	}
	
	public Action getAction() {
		return action;
	}
}
