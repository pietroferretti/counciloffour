package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.message.NewGamePhaseMsg;
import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.Model;

public class GamePhaseView extends Observable implements Observer {

	private GamePhase gamePhaseCopy;

	public GamePhaseView(GamePhase gamePhaseCopy) {
		this.gamePhaseCopy = gamePhaseCopy;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof Model)) {
			throw new IllegalArgumentException();
		}
		if (arg instanceof NewGamePhaseMsg) {
			gamePhaseCopy = ((NewGamePhaseMsg) arg).getNewGamePhase();
			setChanged();
			notifyObservers();
		}

	}

}
