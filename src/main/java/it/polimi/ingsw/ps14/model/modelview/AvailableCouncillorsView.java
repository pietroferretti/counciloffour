package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Model;

/**
 * 
 * It contains an updated copy of the number of available councillors left for
 * each color. It gets an update from the {@link Model}, updates itself and
 * notifies the {@link ModelView}.
 *
 */
public class AvailableCouncillorsView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5160628764761062551L;
	private Map<ColorCouncillor, Integer> availableCouncillorsCopy;

	public AvailableCouncillorsView(Map<ColorCouncillor, Integer> availableCouncillors) {
		availableCouncillorsCopy = new EnumMap<>(availableCouncillors);
	}

	public Map<ColorCouncillor, Integer> getAvailableCouncillorsCopy() {
		return availableCouncillorsCopy;
	}

	@Override
	public void update(Observable o, Object arg) {

		if (!(o instanceof GameBoard)) {
			throw new IllegalArgumentException();
		}

		else {
			availableCouncillorsCopy = new EnumMap<>(((GameBoard) o).getAvailableCouncillors());
			setChanged();
			notifyObservers();
		}
	}

}
