package it.polimi.ingsw.ps14.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import it.polimi.ingsw.ps14.model.bonus.BonusList;

/**
 * This class contains all the bonuses a player can get when leveling up on the
 * nobility track.
 */
public class NobilityTrack extends Observable implements Cloneable {
	/*
	 * TODO Se abbiamo sbatti possiamo fare delle classi per i bonus speciali,
	 * poi caricarli in NobilityTrack all'inizializzazione del gioco +
	 * aggiungere la personalizzazione della nobility track dai settings
	 */
	private final Map<Integer, BonusList> bonusesByLevel;

	public NobilityTrack() {
		bonusesByLevel = new HashMap<>();
	}
	
	public NobilityTrack(Map<Integer, BonusList> bonusesByLevel) {
		this.bonusesByLevel = bonusesByLevel; 
	}

	/**
	 * Given a Player, this method finds the bonus corresponding to their
	 * nobility level and applies it to the Player.
	 * 
	 * @param player
	 *            - The player that needs to get the bonus
	 */
	public boolean useBonus(Player player, GameBoard gameboard) {
		int nobilityLevel = player.getLevel();
		BonusList bonusThisLevel = bonusesByLevel.get(new Integer(nobilityLevel));
		
		if (bonusThisLevel != null) {
			bonusThisLevel.useBonus(player, gameboard);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean bonusExistsAtLevel(int level) {
		return bonusesByLevel.get(level) != null;
	}

	@Override
	public String toString() {
		// TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public NobilityTrack clone() throws CloneNotSupportedException {
		return new NobilityTrack(new HashMap<>(bonusesByLevel));
	}
}