package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.Player;

public class BonusNobility extends Bonus{
	
	public BonusNobility(int quantity){
		super(quantity);
	}
	
	//nobility is player.level
	public void useBonus(Player player) {
		player.upLevel(super.getQuantity());
	}
	
}
