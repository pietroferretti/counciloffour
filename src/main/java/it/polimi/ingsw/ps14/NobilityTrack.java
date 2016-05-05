package it.polimi.ingsw.ps14;

import java.util.ArrayList;
/*
 */
public class NobilityTrack {

	public ArrayList<Bonus> bonuses;

	public Bonus getBonus(int level) {
		return bonuses.get(level);
	}

}