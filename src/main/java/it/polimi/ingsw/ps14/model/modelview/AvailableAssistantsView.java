package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.message.AssistantNumberChangedMsg;
import it.polimi.ingsw.ps14.model.GameBoard;

public class AvailableAssistantsView extends Observable implements Observer {

	private int availableAssistantsCopy;

	public AvailableAssistantsView(int availableAssistantsCopy) {
		this.availableAssistantsCopy = availableAssistantsCopy;
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if (!(o instanceof GameBoard)) {
			throw new IllegalArgumentException();
		}
		
		if (arg instanceof AssistantNumberChangedMsg) {
			availableAssistantsCopy = ((AssistantNumberChangedMsg) arg).getNewNumber();
			setChanged();
			notifyObservers(arg);
		}

	}

}
