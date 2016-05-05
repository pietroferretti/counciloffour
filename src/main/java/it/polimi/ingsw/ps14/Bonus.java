package it.polimi.ingsw.ps14;

public class Bonus {

	private final int assistants;

	private final int coins;

	private final int victoryPoints;

	private final int politicCards;

	private final int nobilityUp;

	private final boolean mainAction;

	public Bonus(int assistants, int coins, int victoryPoints, int politicCards, int nobilityUp, boolean mainAction) {
		this.assistants = assistants;
		this.coins = coins;
		this.victoryPoints = victoryPoints;
		this.politicCards = politicCards;
		this.nobilityUp = nobilityUp;
		this.mainAction = mainAction;
	}

	public int getAssistants() {
		return assistants;
	}

	public int getCoins() {
		return coins;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public int getPoliticCards() {
		return politicCards;
	}

	public int getNobilityUp() {
		return nobilityUp;
	}

	public boolean getmainAction() {
		return mainAction;
	}

}