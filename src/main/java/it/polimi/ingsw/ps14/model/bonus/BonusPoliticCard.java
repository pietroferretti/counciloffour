package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticDeck;

public class BonusPoliticCard implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4552031128366823391L;

	private final int quantity;

	public BonusPoliticCard(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}

	@Override
	public void useBonus(Player player, Model model) {
		PoliticDeck deck = model.getGameBoard().getPoliticDeck();
		for (int i = 0; i < quantity; i++) {
			player.addPolitic(deck.drawCard());
		}
	}
	
	@Override
	public BonusPoliticCard makeCopy() {
		return new BonusPoliticCard(quantity);
	}

	@Override
	public String toString() {
		return "\n+" + Integer.toString(quantity) + " politic card(s)";
	}

}
