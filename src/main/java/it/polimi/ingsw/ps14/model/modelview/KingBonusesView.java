package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.message.KingBonusesChanged;
import it.polimi.ingsw.ps14.model.GameBoard;

/**
 * 
 * NB: Mostro solo il bonus ottenibile non prendo tutta la lista (secondo me
 * alla view non serve conoscere anche gli altri)
 *
 */

public class KingBonusesView extends Observable implements Observer {

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
