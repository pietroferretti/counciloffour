package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.message.NewCurrentPlayerMsg;
import it.polimi.ingsw.ps14.model.Model;

public class CurrentPlayerView extends Observable implements Observer {

	private String currentPlayerNameCopy;
	private int currentPlayerIDCopy;

	public CurrentPlayerView(String currentPlayerNameCopy, int currentPlayerIDCopy) {
		this.currentPlayerNameCopy = currentPlayerNameCopy;
		this.currentPlayerIDCopy = currentPlayerIDCopy;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (!(o instanceof Model)) {
			throw new IllegalArgumentException();
		}
		if (arg instanceof NewCurrentPlayerMsg) {
			currentPlayerIDCopy = ((NewCurrentPlayerMsg) arg).getPlayerID();
			currentPlayerNameCopy = ((NewCurrentPlayerMsg) arg).getPlayerName();
			setChanged();
			notifyObservers((NewCurrentPlayerMsg) arg);
		}

	}

}
