package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusVictoryPoint implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6153823313381004339L;
	
	private final int points;
	
	public BonusVictoryPoint(int points) {
		if (points < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.points = points;
	}

	@Override
	public void useBonus(Player player, Model model) {

		player.addPoints(points);
	}

	@Override 
	public BonusVictoryPoint makeCopy() {
		return new BonusVictoryPoint(points);
	}

	@Override
	public String toString() {
		return "\n+" + Integer.toString(points) + " victory points";
	}

}
