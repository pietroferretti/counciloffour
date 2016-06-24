package it.polimi.ingsw.ps14.message.fromserver;

import java.util.List;

import it.polimi.ingsw.ps14.message.Message;

public class GameEndedMsg implements Message {

	private static final long serialVersionUID = 8661223348429957019L;

	private final Integer winnerID;
	private final List<Integer> playerIDs;
	private final List<String> playerNames;
	private final List<Integer> numAssistants;
	private final List<Integer> numCards;
	private final List<Integer> numEmporiums;
	private final List<Integer> nobilityLevels;
	private final List<Integer> numCoins;

	public GameEndedMsg(List<Integer> playerIDs, List<String> playerNames, List<Integer> numAssistants,
			List<Integer> numCards, List<Integer> numEmporiums, List<Integer> nobilityLevels, List<Integer> numCoins) {
		this.playerIDs = playerIDs;
		this.playerNames = playerNames;
		this.numAssistants = numAssistants;
		this.numCards = numCards;
		this.numEmporiums = numEmporiums;
		this.nobilityLevels = nobilityLevels;
		this.numCoins = numCoins;
		winnerID = playerIDs.get(0);
	}

	/**
	 * @return the winnerID
	 */
	public Integer getWinnerID() {
		return winnerID;
	}

	/**
	 * @return the playerIDs
	 */
	public List<Integer> getPlayerIDs() {
		return playerIDs;
	}

	/**
	 * @return the playerNames
	 */
	public List<String> getPlayerNames() {
		return playerNames;
	}

	/**
	 * @return the numAssistants
	 */
	public List<Integer> getNumAssistants() {
		return numAssistants;
	}

	/**
	 * @return the numCards
	 */
	public List<Integer> getNumCards() {
		return numCards;
	}

	/**
	 * @return the numEmporiums
	 */
	public List<Integer> getNumEmporiums() {
		return numEmporiums;
	}

	/**
	 * @return the nobilityLevels
	 */
	public List<Integer> getNobilityLevels() {
		return nobilityLevels;
	}
	
	/**
	 * @return the numCoins
	 */
	public List<Integer> getNumCoins() {
		return numCoins;
	}


}
