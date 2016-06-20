package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import it.polimi.ingsw.ps14.model.bonus.Bonus;
import it.polimi.ingsw.ps14.model.bonus.SpecialNobilityBonus;

/**
 * This class contains all the bonuses a player can get when leveling up on the
 * nobility track.
 */
public class NobilityTrack extends Observable implements Serializable {

	private static final long serialVersionUID = -6062832074489950572L;
	
	private final Map<Integer, Bonus> bonusesByLevel;

	public NobilityTrack() {
		bonusesByLevel = new HashMap<>();
	}

	public NobilityTrack(Map<Integer, Bonus> bonusesByLevel) {
		this.bonusesByLevel = bonusesByLevel;
	}

	public NobilityTrack(NobilityTrack nt) {

		bonusesByLevel = new HashMap<>(nt.bonusesByLevel.size());

		for (Map.Entry<Integer, Bonus> entry : nt.bonusesByLevel.entrySet()) {
			this.bonusesByLevel.put(entry.getKey(), entry.getValue().makeCopy());
		}
	}

	/**
	 * Given a Player, this method finds the bonus corresponding to their
	 * nobility level and applies it to the Player.
	 * 
	 * @param player
	 *            - The player that needs to get the bonus
	 */
	public boolean useBonus(Player player, Model model) {
		int nobilityLevel = player.getLevel();
		Bonus bonusThisLevel = bonusesByLevel.get(new Integer(nobilityLevel));

		if (bonusThisLevel != null) {
			bonusThisLevel.useBonus(player, model);
			setChanged();
			notifyObservers();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean useBonus(Player player, Model model, int nobilityLevel) {
		Bonus bonusThisLevel = bonusesByLevel.get(new Integer(nobilityLevel));

		if (bonusThisLevel != null) {
			bonusThisLevel.useBonus(player, model);
			setChanged();
			notifyObservers();
			return true;
		} else {
			return false;
		}
	}

	public boolean bonusExistsAtLevel(int level) {
		return bonusesByLevel.get(level) != null;
	}

	public boolean isBonusSpecial(int level) {
		return bonusesByLevel.get(level) instanceof SpecialNobilityBonus;
	}
	
	@Override
	public String toString() {
		String s = "NOBILITY TRACK:%n";
		for (Map.Entry<Integer, Bonus> entry : bonusesByLevel.entrySet()) {
			s = s + "%nlevel: " + Integer.toString(entry.getKey()) + " bonus: " + entry.getValue().toString();
		}
		return s;
	}

}