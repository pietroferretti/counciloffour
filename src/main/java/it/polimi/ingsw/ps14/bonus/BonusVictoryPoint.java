package it.polimi.ingsw.ps14.bonus;

import it.polimi.ingsw.ps14.Player;

public class BonusVictoryPoint extends Bonus {

	public void useBonus(Player player) {
		player.addPoints(super.getQuantity());
	}
	
	public BonusVictoryPoint(int quantity){
		super(quantity);
	}
}
