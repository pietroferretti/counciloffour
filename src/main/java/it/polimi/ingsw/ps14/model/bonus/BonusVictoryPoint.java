package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.Player;

public class BonusVictoryPoint extends Bonus {

	public BonusVictoryPoint(int quantity){
		super(quantity);
	}
	
	public void useBonus(Player player) {
		player.addPoints(super.getQuantity());
	}

}
