package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

import java.util.ArrayList;
import java.util.List;

public class BonusFromTokens extends SpecialNobilityBonus {

	public BonusFromTokens(int quantity) {
		super(quantity);
	}



	// TODO mi serve un modo per comunicare con il giocatore
	/**
	 * The player obtains the bonus from one (or more) of the tokens from the
	 * cities where he has already built an emporium. The player cannot choose
	 * one that would advance him in the nobility track.
	 * 
	 * @param player
	 *            The player that got the bonus
	 * @param model
	 *            The current gameboard, to get the cities
	 */
	@Override
	public void useBonus(Player player, Model model) {
		boolean isValid;
		List<BonusList> bonusChoosable = new ArrayList<>();
		for (City c : model.getGameBoard().getCities()) {
			isValid = true;
			if (c.isEmporiumBuilt(player)) {
				for (Bonus b : c.getToken().getBonusCard())
					if (b instanceof BonusNobility)
						isValid = false;
				if (isValid)
					bonusChoosable.add(c.getToken());
			}
		}
		
		

		// se quantity > tokens ??

		// display a list of tokens to the player, remove nobility upgrades

		// aspetta la scelta del giocatore (anche multipla)
		// se multipli check che siano diversi

		// prendi i token corrispondenti, applicali al giocatore
	}
}
