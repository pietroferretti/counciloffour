package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;

public class BonusCoin extends Bonus {
	
	public BonusCoin(int quantity){
		super(quantity);
	}

	public void useBonus(Player player, GameBoard gameboard) {
		player.addCoins(super.getQuantity());
	}
}
