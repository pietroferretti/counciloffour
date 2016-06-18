package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.Market;

public class MarketUpdatedMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1468464704676645330L;

	private Market updatedMarket;

	public MarketUpdatedMsg(Market updatedMarket) {
		this.updatedMarket = updatedMarket;
	}

	public Market getUpdatedMarket() {
		return updatedMarket;
	}

}
