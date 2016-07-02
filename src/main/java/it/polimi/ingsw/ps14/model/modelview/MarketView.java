package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.Market;

/**
 * 
 * It contains an updated copy of the {@link Market}. It gets an update from the
 * {@link Market}, updates itself and notifies the {@link ModelView}.
 *
 */
public class MarketView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4912625828834707577L;

	private Market marketCopy;

	public MarketView(Market marketCopy) {
		this.marketCopy = new Market(marketCopy);
	}

	public Market getMarketCopy() {
		return marketCopy;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof Market)) {
			throw new IllegalArgumentException();
		} else {
			marketCopy = new Market((Market) o);
			setChanged();
			notifyObservers(arg);
		}

	}

}
