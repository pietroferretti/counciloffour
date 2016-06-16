package it.polimi.ingsw.ps14.message;

import it.polimi.ingsw.ps14.model.MarketState;

public class MarketStateUpdatedMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5353701158107411512L;

	private MarketState updatedMarketState;

	public MarketStateUpdatedMsg(MarketState updatedMarketState) {
		this.updatedMarketState = updatedMarketState;
	}

	public MarketState getUpdatedMarketState() {
		return updatedMarketState;
	}
}
