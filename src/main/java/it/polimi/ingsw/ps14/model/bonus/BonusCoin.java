package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.Player;

public class BonusCoin extends Bonus {
	
	public BonusCoin(int quantity){
		super(quantity);
	}

	public void useBonus(Player player) {
		player.addCoins(super.getQuantity());
	}
	
	
}
