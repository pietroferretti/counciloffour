package it.polimi.ingsw.ps14.classiDaEliminare;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.Model;

public class GamePhaseView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2399337825927999620L;
	private GamePhase gamePhaseCopy;

	public GamePhaseView(GamePhase gamePhaseCopy) {
		this.gamePhaseCopy = gamePhaseCopy;
	}

	public GamePhase getGamePhaseCopy() {
		return gamePhaseCopy;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof Model)) {
			throw new IllegalArgumentException();
		} else {
			gamePhaseCopy = ((Model) o).getGamePhase();
			setChanged();
			notifyObservers();
		}

	}

}
