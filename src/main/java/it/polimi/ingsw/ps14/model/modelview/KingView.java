package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.model.King;
import it.polimi.ingsw.ps14.model.Settings;

public class KingView extends Observable implements Observer {
	private static final Logger LOGGER= Logger.getLogger(Settings.class.getName());
	
	King kingCopy;

	public KingView(King kingCopy) {
		this.kingCopy = kingCopy;
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
	public void update(Observable o, Object arg){

		// TODO controllare

		if (!(o instanceof King)) {
			throw new IllegalArgumentException();
		}
		try {
			setKingCopy(((King) o).clone());
		} catch (CloneNotSupportedException e) {
			LOGGER.log(Level.SEVERE, "Couldn't copy King.", e);
		}

	}

}
