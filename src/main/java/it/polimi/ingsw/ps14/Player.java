package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

	private String color;
	private int coins;
	private PoliticDeck hand;
	private int level;
	private int points;
	private ArrayList<BusinessPermit> permitTiles;
	private ArrayList<BusinessPermit> usedPermitTiles;
	private int assistants;

	public Player(PoliticDeck hand, ArrayList<BusinessPermit> permitTiles, int coins, int assistants) {
		chooseColor();
		this.coins = coins;
		this.assistants = assistants;
		level = 0;
		points = 0;
		this.hand = hand;
		this.permitTiles = permitTiles;
		usedPermitTiles = null;
	}

	public void chooseColor() {
		// TODO Should we read it from the config file instead?
		// or maybe we should ask for a player NAME .-.

		// terminale
		Scanner input = new Scanner(System.in);
		System.out.println("Choose your color: "); // dovremmo mettere dei
													// controlli?? se mettiamo
													// nome invece di colore
													// viene più facile
		color = input.next();
		input.close();
	}

	public boolean useCoins(int coins) {
		if (this.coins >= coins) {
			this.coins = this.coins - coins;
			return true;
		} else
			return false;
	}

	public int getCoins() {
		return coins;
	}

	public boolean useAssistant(int assistantNumber) {
		if (assistants >= assistantNumber) {
			assistants = assistants - assistantNumber;
			return true;
		} else
			return false;
	}

	public int getAssistant() {
		return assistants;
	}

	public String getColor() {
		return color;
	}

	public int getLevel() {
		return level;
	}

	public void upLevel() {
		level++;
	}

	public void acquireBusinessPermit(BusinessDeck deck) {
		permitTiles.add(deck.drawCard());
		// TODO possibili errori?
		// tipo mazzo che finisce ecc li controlliamo prima di invocarlo?
	}

	public PoliticDeck getHand() {
		return hand;
	}

	public int getPoints() {
		return points;
	}

	public int getAssistants() {
		return assistants;
	}
}