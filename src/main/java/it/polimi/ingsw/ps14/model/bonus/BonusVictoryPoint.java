package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusVictoryPoint extends Bonus {

	public BonusVictoryPoint(int quantity){
		super(quantity);
	}
	
	@Override
	public void useBonus(Player player, Model model) {
		player.addPoints(super.getQuantity());
	}

}
