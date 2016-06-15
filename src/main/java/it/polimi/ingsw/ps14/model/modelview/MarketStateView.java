package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.message.NewMarketStateMsg;
import it.polimi.ingsw.ps14.model.MarketState;
import it.polimi.ingsw.ps14.model.Model;

public class MarketStateView extends Observable implements Observer {

	private MarketState currentMarketStateCopy;

	public MarketStateView(MarketState currentMarketStateCopy) {
		this.currentMarketStateCopy = currentMarketStateCopy;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof Model)) {
			throw new IllegalArgumentException();
		}
		if (arg instanceof NewMarketStateMsg) {
			currentMarketStateCopy = ((NewMarketStateMsg) arg).getNewMarketState();
			setChanged();
			notifyObservers(arg);
		}

	}
}
