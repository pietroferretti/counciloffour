package it.polimi.ingsw.ps14;

public class Player {

	private String color;
	private int coins;
	private PoliticDeck hand;
	private int level;
	private int points;
	private BusinessDeck permitTiles;
	private PoliticDeck myPoliticDeck;
	private int assistants;

	public void chooseColor() {
	}

	public void useCoins() {
	}

	public int getCoins() {
		return 0;
	}

	public void useAssistant(int assistantNumber) {
		// TODO: exception if assistants is too low
		assistants = assistants - assistantNumber;
	}

	public int getAssistant() {
		return 0;
	}

	public Player(String color, PoliticDeck hand, int coins, int assistants) {
	}

	public String getColor() {
		return null;
	}

	public int getLevel() {
		return 0;
	}

	public void upLevel() {
	}

	public void acquireBusinessPermit() {
	}

}