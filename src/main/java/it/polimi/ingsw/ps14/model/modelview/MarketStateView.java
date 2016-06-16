package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.MarketState;
import it.polimi.ingsw.ps14.model.Model;

public class MarketStateView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4293565938326166904L;
	private MarketState currentMarketStateCopy;

	public MarketStateView(MarketState currentMarketStateCopy) {
		this.currentMarketStateCopy = currentMarketStateCopy;
	}

	public MarketState getCurrentMarketStateCopy() {
		return currentMarketStateCopy;
	}

	@Override
	public void update(Observable o, Object arg) {

		if (!(o instanceof Model)) {
			throw new IllegalArgumentException();
		}

		else {
			currentMarketStateCopy = ((Model) o).getCurrentMarketState();
			setChanged();
			notifyObservers();
		}

	}
}
