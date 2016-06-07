package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;

public class BonusTakeBusinessPermit extends SpecialNobilityBonus {
	
	public BonusTakeBusinessPermit(int quantity) {
		super(quantity);
	}
	
	// TODO mi serve un modo per comunicare con il giocatore
	/**
	 * The player can get a free permit from the board.
	 * @param player The player that got the bonus
	 * @param gameboard The current gameboard, to get the business permits
	 */
	public void useBonus(Player player, GameBoard gameboard) {
		for(int i=0; i<super.getQuantity(); i++) {
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
}
