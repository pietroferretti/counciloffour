package it.polimi.ingsw.ps14.message.fromclient;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.actions.market.BuyAction;

public class BuyMsg implements Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5207075985772708351L;
	
	private BuyAction action;

	public BuyMsg(BuyAction action) {
		this.action = action;
	}
	
	public BuyAction getAction() {
		return action;
	}
	
}
