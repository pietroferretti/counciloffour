package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;

public class BonusNobility extends Bonus{
	
	public BonusNobility(int quantity){
		super(quantity);
	}
	
	//nobility is player.level
	public void useBonus(Player player, GameBoard gameboard) {
		player.levelUp(super.getQuantity());
	}
	
}
