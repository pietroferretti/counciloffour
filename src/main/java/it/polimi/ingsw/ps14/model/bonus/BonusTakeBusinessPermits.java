package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusTakeBusinessPermits implements SpecialNobilityBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6778943163874682668L;

	private final int quantity;

	public BonusTakeBusinessPermits(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}

	// TODO mi serve un modo per comunicare con il giocatore
	/**
	 * The player can get a free permit from the board.
	 * 
	 * @param player
	 *            The player that got the bonus
	 * @param model
	 *            The current gameboard, to get the business permits
	 */
	@Override
	public void useBonus(Player player, Model model) {
		for (int i = 0; i < quantity; i++) {
			// per ogni regione
			// prendi i permit disponibili

			// tieni una lista dei permit disponibili

			// mostra all'utente i permit disponibili

			// aspetta la scelta dell'utente (1-6 ?)

			// riconosci la regione del permit
			// prendi il permit, rimuovilo dalla regione
			// dai il permit all utente
			// region.getBusinessPermits().fillEmptySpots()

		}
	}

	@Override
	public BonusTakeBusinessPermits makeCopy() {
		return new BonusTakeBusinessPermits(quantity);
	}

	@Override
	public String toString() {
		return "\n+" + Integer.toString(quantity) + " business permits!";
	}
}
