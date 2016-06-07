package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Player;

public class BonusAssistant extends Bonus {

	public BonusAssistant(int quantity){
		super(quantity);
	}
	
	public void useBonus(Player player) {
		player.addAssistants(super.getQuantity());
	}

}
