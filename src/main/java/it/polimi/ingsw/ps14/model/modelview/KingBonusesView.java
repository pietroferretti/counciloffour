package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.NobilityTrack;

/**
 * 
 * It contains an updated copy of the next obtainable King Bonus contained in
 * the {@link Model}. It gets an update from the {@link Model} when a bonus is
 * used, updates itself and notifies the {@link ModelView}.
 *
 */
public class KingBonusesView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6832136279230385710L;
	private int showableKingBonus;

	public KingBonusesView(int showableKingBonus) {
		this.showableKingBonus = showableKingBonus;
	}

	public int getShowableKingBonus() {
		return showableKingBonus;
	}

	@Override
	public void update(Observable o, Object arg) {

		if (!(o instanceof GameBoard)) {
			throw new IllegalArgumentException();
		}

		else {
			showableKingBonus = ((GameBoard) o).getKingBonuses().peek();
			setChanged();
			notifyObservers();
		}

	}

}
