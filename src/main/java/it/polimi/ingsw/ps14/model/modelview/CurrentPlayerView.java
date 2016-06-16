package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.Model;

public class CurrentPlayerView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4547112770153965861L;
	private String currentPlayerNameCopy;
	private int currentPlayerIDCopy;

	public CurrentPlayerView(String currentPlayerNameCopy, int currentPlayerIDCopy) {
		this.currentPlayerNameCopy = currentPlayerNameCopy;
		this.currentPlayerIDCopy = currentPlayerIDCopy;
	}

	public String getCurrentPlayerNameCopy() {
		return currentPlayerNameCopy;
	}

	public int getCurrentPlayerIDCopy() {
		return currentPlayerIDCopy;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof Model)) {
			throw new IllegalArgumentException();
		} else {
			currentPlayerIDCopy = ((Model) o).getCurrentPlayer().getId();
			currentPlayerNameCopy = ((Model) o).getCurrentPlayer().getName();
			setChanged();
			notifyObservers();
		}

	}

}
