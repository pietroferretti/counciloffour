package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticDeck;

public class BonusPoliticCard extends Bonus {
		
	public BonusPoliticCard(int quantity){
		super(quantity);
	}
	
	public void useBonus(Player player, PoliticDeck politicDeck) {
		for (int i=0;i<super.getQuantity();i++)
			player.addPolitic(politicDeck.drawCard());
	}
	
}
