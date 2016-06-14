package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusNobility extends Bonus{
	
	public BonusNobility(int quantity){
		super(quantity);
	}
	
	//nobility is player.level
	@Override
	public void useBonus(Player player, Model model) {
		player.upLevel(super.getQuantity());
	}
	
}
