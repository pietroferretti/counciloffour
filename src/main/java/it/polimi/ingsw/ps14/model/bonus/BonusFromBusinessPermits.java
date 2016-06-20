package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusFromBusinessPermits implements SpecialNobilityBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -254514472185231405L;

	private final int quantity;

	public BonusFromBusinessPermits(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}

	// TODO more than one?
	/**
	 * The player obtains the bonus of one (or more) of the business permits he
	 * prevously bought.
	 * 
	 * @param player
	 *            the player that got the bonus
	 * @param model 
	 * 			  the game model
	 */
	@Override
	public void useBonus(Player player, Model model) {
		for (int i = 0; i < quantity; i++) {
			// prendi tutti i business permit del giocatore (usati e non usati)

			// mostra all'utente la lista

			// aspetta la scelta dell'utente

			// trova business permit corrispondente alla scelta
			// applica bonus

			// fine

		}
	}

	@Override
	public BonusFromBusinessPermits makeCopy() {
		return new BonusFromBusinessPermits(quantity);
	}

}
