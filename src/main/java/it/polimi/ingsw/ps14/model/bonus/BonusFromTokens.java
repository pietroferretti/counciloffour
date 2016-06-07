package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;

public class BonusFromTokens extends SpecialNobilityBonus {
	
	public BonusFromTokens(int quantity) {
		super(quantity);
	}
	
	// TODO mi serve un modo per comunicare con il giocatore
	/**
	 * The player obtains the bonus from one (or more) of the tokens 
	 * from the cities where he has already built an emporium. 
	 * The player cannot choose one that would advance him in the nobility track.
	 * @param player The player that got the bonus
	 * @param gameboard The current gameboard, to get the cities
	 */
	public void useBonus(Player player, GameBoard gameboard) {
		
		// find all the cities where the player has built
		// get all the tokens
		
			// se quantity > tokens ??
		
		// display a list of tokens to the player, remove nobility upgrades
		
		// aspetta la scelta del giocatore (anche multipla)
			// se multipli check che siano diversi
		
		// prendi i token corrispondenti, applicali al giocatore
	}
}
