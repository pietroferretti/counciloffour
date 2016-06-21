package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.GameBoard;

public class AvailableAssistantsView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4242169201722704995L;
	private int availableAssistantsCopy;

	public AvailableAssistantsView(int availableAssistantsCopy) {
		this.availableAssistantsCopy = availableAssistantsCopy;
	}

	public int getAvailableAssistantsCopy() {
		return availableAssistantsCopy;
	}

	private void setAvailableAssistantsCopy(int availableAssistantsCopy) {
		this.availableAssistantsCopy = availableAssistantsCopy;
		setChanged();
		notifyObservers();

	}

	@Override
	public void update(Observable o, Object arg) {

		if (!(o instanceof GameBoard)) {
			throw new IllegalArgumentException();
		}

		else {
			setAvailableAssistantsCopy(((GameBoard) o).getAvailableAssistants());
		}

	}

}
