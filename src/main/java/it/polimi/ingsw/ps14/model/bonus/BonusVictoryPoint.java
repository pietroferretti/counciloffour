package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusVictoryPoint implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6153823313381004339L;
	
	private final int points;
	private boolean used;
	
	public BonusVictoryPoint(int points) {
		if (points < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.points = points;
	}

	@Override
	public void useBonus(Player player, Model model) {

		player.addPoints(points);
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed() {
		used = true;
	}
	
	@Override 
	public BonusVictoryPoint makeCopy() {
		return new BonusVictoryPoint(points);
	}

	@Override
	public String toString() {
		return "+" + Integer.toString(points) + " victory points\n";
	}

}
