package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticDeck;

public class BonusPoliticCard extends Bonus {
		
	public BonusPoliticCard(int quantity){
		super(quantity);
	}
	
	public void useBonus(Player player, GameBoard gameboard) {
		PoliticDeck deck = gameboard.getPoliticDeck();
		for (int i=0;i<super.getQuantity();i++) {
			player.addPolitic(deck.drawCard());
		}
	}
	
}
