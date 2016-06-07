package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;

public class BonusMainAction extends Bonus{
	
	public BonusMainAction(int quantity){
		super(quantity);
	}
	
	public void useBonus(Player player, GameBoard gameboard){
		for(int i=0;i<super.getQuantity();i++){
			player.additionalMainsToDo++;
		}
	}
}
