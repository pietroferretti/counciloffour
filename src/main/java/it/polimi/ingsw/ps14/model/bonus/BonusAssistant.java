package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.Player;

public class BonusAssistant extends Bonus {

	public void useBonus(Player player) {
		player.addAssistants(super.getQuantity());
	}
	
	public BonusAssistant(int quantity){
		super(quantity);
	}

}
