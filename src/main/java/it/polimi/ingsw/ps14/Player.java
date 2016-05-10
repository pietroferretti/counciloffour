package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

	private final String name;
	private final String color; // TODO: magari cambiare da stringa a qualche
								// tipo come java.awt.Color boh
	private int coins;
	private int assistants;
	private int level; // nobility
	private int points;
	private ArrayList<PoliticCard> hand;
	private ArrayList<BusinessPermit> permitTiles;
	private ArrayList<BusinessPermit> usedPermitTiles;

	public Player(String name, String color, int coins, int assistants, PoliticDeck deck, int numberOfCards) {
		this.name = name;
		this.color = color;
		this.coins = coins;
		this.assistants = assistants;
		level = 0;
		points = 0;
		this.hand = deck.drawMultipleCards(numberOfCards);
		permitTiles = new ArrayList<BusinessPermit>();
		usedPermitTiles = new ArrayList<BusinessPermit>();
	}

	public Player(String name, String color) {
		this.name = name;
		this.color = color;
		coins = 0;
		assistants = 0;
		level = 0;
		points = 0;
		hand = new ArrayList<PoliticCard>();
		permitTiles = new ArrayList<BusinessPermit>();
		usedPermitTiles = new ArrayList<BusinessPermit>();
	}

	public void chooseColor() {

		// se dobbiamo chiederlo al giocatore mi sa che è meglio chiederlo
		// insieme al nome quando il giocatore si collega
		// non in questa classe

		// terminale
		/*
		 * Scanner input = new Scanner(System.in); System.out.println(
		 * "Choose your color: "); // dovremmo mettere dei // controlli?? se
		 * mettiamo // nome invece di colore // viene più facile color =
		 * input.next(); input.close();
		 */
	}

	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
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

	public void addCoins(int coins) {
		this.coins = this.coins + coins;
	}

	public boolean useAssistants(int assistantNumber) {
		if (assistants >= assistantNumber) {
			assistants = assistants - assistantNumber;
			return true;
		} else
			return false;
	}

	public int getAssistants() {
		return assistants;
	}

	public void addAssistants(int assistants) {
		this.assistants = this.assistants + assistants;
	}

	public int getLevel() {
		return level;
	}

	public int upLevel() {
		level++;
		return level;	// returns the new value of level to check bonuses
	}
	
	public int getPoints() {
		return points;
	}

	public void addPoints(int points) {
		this.points = this.points + points;
	}
	
	public ArrayList<PoliticCard> getHand() {
		return hand;
	}
	
	public int getNumberOfCards() {
		return hand.size();
	}
	
	public void addPolitic(PoliticCard card) {
		hand.add(card);
	}

//	public void Card(PoliticDeck deck) {
//		hand.add(deck.drawCard());
//		// TODO possibili errori?
//		// tipo mazzo che finisce ecc li controlliamo prima di invocarlo?
//	}
	
	// sì può accorpare all'altro metodo con polimorfismo?
	public void drawMultipleCards(PoliticDeck deck, int numberOfCards) {
		hand.addAll(deck.drawMultipleCards(numberOfCards));
	}

	public void useCards(){
		//???
	}
	
	public void acquireBusinessPermit(BusinessPermit permitTile) {
		permitTiles.add(permitTile);
	}
	
	public int getNumberOfPermits() {
		return permitTiles.size() + usedPermitTiles.size();
	}

}