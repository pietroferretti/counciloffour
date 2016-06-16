package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusFromBusinessPermits extends SpecialNobilityBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -254514472185231405L;

	public BonusFromBusinessPermits(int quantity) {
		super(quantity);
	}

	// TODO mi serve un modo per comunicare con il giocatore
	// TODO more than one?
	/**
	 * The player obtains the bonus of one (or more) of the business permits he
	 * prevously bought.
	 * 
	 * @param player
	 *            The player that got the bonus
	 */
	@Override
	public void useBonus(Player player, Model model) {
		for (int i = 0; i < super.getQuantity(); i++) {
			// prendi tutti i business permit del giocatore (usati e non usati)

			// mostra all'utente la lista

			// aspetta la scelta dell'utente

			// trova business permit corrispondente alla scelta
			// applica bonus

			// fine

		}
	}
}
