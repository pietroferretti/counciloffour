package it.polimi.ingsw.ps14;

import java.util.HashMap;

import it.polimi.ingsw.ps14.model.bonus.Bonus;

public class NobilityTrack {

	private final HashMap<Integer, Bonus> bonuses;

	public NobilityTrack(HashMap<Integer, Bonus> bonuses) {
		this.bonuses = bonuses;
	}

	public Bonus getBonus(int level) {
		return bonuses.get(level);
	}
}