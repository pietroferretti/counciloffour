package it.polimi.ingsw.ps14.message.fromclient;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.actions.market.SellAction;

public class SellMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8608834184440016388L;
	private SellAction action;

	public SellMsg(SellAction action) {
		this.action = action;
	}

	public SellAction getAction() {
		return action;
	}

}
