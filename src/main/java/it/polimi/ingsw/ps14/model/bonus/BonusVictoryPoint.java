package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;

public class BonusVictoryPoint extends Bonus {

	private boolean used;

	public BonusVictoryPoint(int quantity) {
		super(quantity);
		used = false;
	}

	public void useBonus(Player player, GameBoard gameboard) {
		player.addPoints(super.getQuantity());
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed() {
		used = true;
	}

}
