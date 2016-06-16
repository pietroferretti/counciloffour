package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticDeck;

public class BonusPoliticCard extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4552031128366823391L;

	public BonusPoliticCard(int quantity) {
		super(quantity);
	}

	@Override
	public void useBonus(Player player, Model model) {
		PoliticDeck deck = model.getGameBoard().getPoliticDeck();
		for (int i = 0; i < super.getQuantity(); i++) {
			player.addPolitic(deck.drawCard());
		}
	}

	@Override
	public String toString() {
		return "+" + Integer.toString(getQuantity()) + " politic card(s)%n";
	}

}
