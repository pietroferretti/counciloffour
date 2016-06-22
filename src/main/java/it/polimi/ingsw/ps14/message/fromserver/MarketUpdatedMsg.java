package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.Market;

public class MarketUpdatedMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1468464704676645330L;

	private Market updatedMarket;
	private String notice;

	public MarketUpdatedMsg(Market updatedMarket, String notice) {
		super();
		this.updatedMarket = updatedMarket;
		this.notice = notice;
	}

	public Market getUpdatedMarket() {
		return updatedMarket;
	}

	public String getNotice() {
		return notice;
	}

	@Override
	public String toString() {
		return notice + updatedMarket;
	}
}
