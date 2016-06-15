package it.polimi.ingsw.ps14.model.modelview;

import java.util.EnumMap;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.message.AvailableCouncillorsChangedMsg;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.GameBoard;

public class AvailableCouncillorsView extends Observable implements Observer {

	private EnumMap<ColorCouncillor, Integer> availableCouncillorsCopy;

	public AvailableCouncillorsView(EnumMap<ColorCouncillor, Integer> availableCouncillors) {
		this.availableCouncillorsCopy = availableCouncillors;
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if (!(o instanceof GameBoard)) {
			throw new IllegalArgumentException();
		}
		
		if (arg instanceof AvailableCouncillorsChangedMsg) {
			availableCouncillorsCopy = new EnumMap<>(((AvailableCouncillorsChangedMsg) arg).getNewValues());
		}
	}

}
