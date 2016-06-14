package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;


public class BonusList {

	private final List<Bonus> bonuses;

	public BonusList(List<Bonus> bonuses) {
		this.bonuses = bonuses;
	}

	public BonusList(Bonus bonus1) {
		bonuses = new ArrayList<>();
		bonuses.add(bonus1);
	}

	public BonusList(Bonus bonus1, Bonus bonus2) {
		bonuses = new ArrayList<>();
		bonuses.add(bonus1);
		bonuses.add(bonus2);
	}

	public BonusList(BonusList bl) {
		this.bonuses = new ArrayList<>();
		for (Bonus bon : bl.bonuses) {
			if (bon instanceof BonusAssistant)
				this.bonuses.add(new BonusAssistant(bon.getQuantity()));
			else if (bon instanceof BonusCoin)
				this.bonuses.add(new BonusCoin(bon.getQuantity()));
			else if (bon instanceof BonusMainAction)
				this.bonuses.add(new BonusMainAction(bon.getQuantity()));
			else if (bon instanceof BonusNobility)
				this.bonuses.add(new BonusNobility(bon.getQuantity()));
			else if (bon instanceof BonusPoliticCard)
				this.bonuses.add(new BonusPoliticCard(bon.getQuantity()));
			else if (bon instanceof BonusVictoryPoint)
				this.bonuses.add(new BonusVictoryPoint(bon.getQuantity()));
		}
	}

	public List<Bonus> getBonusCard() {
		return bonuses;
	}
<<<<<<< HEAD
	
/**
 * use bonus according bonus type
 * @param player player who use the bonus
 * @param deck politicDeck of the gameboard
 */
	public void useBonus(Player player, Model model){
		for(Bonus bonus : bonuses){
			bonus.useBonus(player, model);
=======

	/**
	 * use bonus according bonus type
	 * 
	 * @param player
	 *            player who use the bonus
	 * @param deck
	 *            politicDeck of the gameboard
	 */
	public void useBonus(Player player, Model model) {
		for (Bonus bonus : bonuses) {
			bonus.useBonus(player, gameboard);
>>>>>>> branch 'master' of https://Federico_Oldani@bitbucket.org/jumpingspiders/counciloffour.git
		}
	}

	@Override
	public String toString() {
		String s = null;
		for (Bonus bon : bonuses) {
			s = s + bon.toString() + " ";
		}
		return s;
	}
}
