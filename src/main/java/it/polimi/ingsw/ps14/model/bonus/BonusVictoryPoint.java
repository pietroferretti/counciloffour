package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;

public class BonusVictoryPoint extends Bonus {

	public BonusVictoryPoint(int quantity){
		super(quantity);
	}
	
	public void useBonus(Player player, GameBoard gameboard) {
		player.addPoints(super.getQuantity());
	}

}
