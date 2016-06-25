package it.polimi.ingsw.ps14.model.bonus;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusList implements Bonus {

	private static final long serialVersionUID = -4986740288070659066L;

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
		
		//TODO
		this.bonuses = new ArrayList<>();

		if (bl.bonuses != null) {
			for (Bonus bon : bl.bonuses) {
				this.bonuses.add(bon.makeCopy());
			}
		}
	}
	
	@Override
	public BonusList makeCopy() {
		return new BonusList(this);
	}

	public List<Bonus> getListOfBonuses() {
		return bonuses;
	}

	public Bonus getBonus(int index) {
		return bonuses.get(index);
	}

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
			bonus.useBonus(player, model);
		}
	}

	@Override
	public String toString() {
		String s = "";
		for (Bonus bon : bonuses) {
			s = s + bon.toString();
		}
		return s;
	}
}
