package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.State;

public class StateView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1938475767822222112L;

	private State stateCopy;

	public StateView(State originalState) {
		this.stateCopy = new State(originalState);
	}

	public State getStateCopy() {
		return stateCopy;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof State)) {
			throw new IllegalArgumentException();
		} else {
			stateCopy = new State((State) o);
			setChanged();
			notifyObservers();
		}

	}

}
