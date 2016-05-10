package it.polimi.ingsw.ps14;

import java.util.ArrayList;

public class NobilityTrack {

	private final ArrayList<Bonus> bonuses;
	
	public NobilityTrack(ArrayList<Bonus> bonuses) {
		this.bonuses = bonuses;
	}
	
	public Bonus getBonus(int level) {
		return bonuses.get(level);
	}

}