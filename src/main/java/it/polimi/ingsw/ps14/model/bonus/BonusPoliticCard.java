package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.PoliticDeck;

public class BonusPoliticCard extends Bonus {
		
	public void useBonus(Player player, PoliticDeck politicDeck) {
		for (int i=0;i<super.getQuantity();i++)
			player.addPolitic(politicDeck.drawCard());
	}
	
	public BonusPoliticCard(int quantity){
		super(quantity);
	}
	
	
	
}
