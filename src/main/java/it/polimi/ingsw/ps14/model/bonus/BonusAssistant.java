package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;

public class BonusAssistant extends Bonus {

	public BonusAssistant(int quantity){
		super(quantity);
	}
	
	public void useBonus(Player player, GameBoard gameboard) {
		gameboard.useAssistants(super.getQuantity());
		player.addAssistants(super.getQuantity());
	}

}
