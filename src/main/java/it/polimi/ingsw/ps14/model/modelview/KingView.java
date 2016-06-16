package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.King;

public class KingView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7051433035809582386L;
	King kingCopy;

	public KingView(King kingCopy) {
		this.kingCopy = new King(kingCopy);
	}

	public King getKingCopy() {
		return kingCopy;
	}

	private void setKingCopy(King kingCopy) {
		this.kingCopy = kingCopy;
		setChanged();
		notifyObservers();
	}

	@Override
	public void update(Observable o, Object arg) {

		if (!(o instanceof King)) {
			throw new IllegalArgumentException();
		} else {
			setKingCopy(new King((King) o));
		}

	}

}
