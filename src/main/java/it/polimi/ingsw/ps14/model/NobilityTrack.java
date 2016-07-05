package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Observable;
import java.util.SortedMap;
import java.util.TreeMap;

import it.polimi.ingsw.ps14.model.bonus.Bonus;

/**
 * This class contains all the bonuses a player can get when leveling up on the
 * nobility track.
 */
public class NobilityTrack extends Observable implements Serializable {

	private static final long serialVersionUID = -6062832074489950572L;

	private final SortedMap<Integer, Bonus> bonusesByLevel;

	public NobilityTrack() {
		bonusesByLevel = new TreeMap<>();
	}

	public NobilityTrack(SortedMap<Integer, Bonus> bonusesByLevel) {
		this.bonusesByLevel = bonusesByLevel;
	}

	public NobilityTrack(NobilityTrack nt) {

		bonusesByLevel = new TreeMap<>();

		for (SortedMap.Entry<Integer, Bonus> entry : nt.bonusesByLevel.entrySet()) {
			this.bonusesByLevel.put(entry.getKey(), entry.getValue().makeCopy());
		}
	}

	public boolean bonusExistsAtLevel(int level) {
		return bonusesByLevel.get(level) != null;
	}

	public Bonus getBonus(int level) {
		return bonusesByLevel.get(level);
	}

	public SortedMap<Integer, Bonus> getBonusesByLevel() {
		return bonusesByLevel;
	}

	@Override
	public String toString() {
		String s = "\nNOBILITY TRACK:\n";
		for (Map.Entry<Integer, Bonus> entry : bonusesByLevel.entrySet()) {
			s = s + "\n" + Integer.toString(entry.getKey()) + ")\n" + entry.getValue().toString() + "\n";
		}
		return s;
	}

}