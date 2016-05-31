package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private final String name;
	private final String color; // TODO: magari cambiare da stringa a qualche
								// tipo come java.awt.Color boh
	private int coins;
	private int assistants;
	private int level; // nobility
	private int points;
//	private List<BusinessPermit> permitTiles;
//	private List<BusinessPermit> usedPermitTiles;
	private List<PoliticCard> hand;
	private BusinessCardsPlayer businessHand;
	
	public int additionalMainsToDo;

	public Player(String name, String color, int coins, int assistants, PoliticDeck deck, int numberOfCards) {
		this.name = name;
		this.color = color;
		this.coins = coins;
		this.assistants = assistants;
		level = 0;
		points = 0;
		this.hand = deck.drawMultipleCards(numberOfCards);
		businessHand=new BusinessCardsPlayer();
//		permitTiles = new ArrayList<>();
//		usedPermitTiles = new ArrayList<>();
	}

	public Player(String name, String color) {
		this.name = name;
		this.color = color;
		coins = 0;
		assistants = 0;
		level = 0;
		points = 0;
//		permitTiles = new ArrayList<>();
//		usedPermitTiles = new ArrayList<>();
		hand = new ArrayList<>();
		businessHand = new BusinessCardsPlayer();
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public int getCoins() {
		return coins;
	}
	
	public boolean useCoins(int coins) {
		if (this.coins >= coins) {
			this.coins = this.coins - coins;
			return true;
		} else
			return false;
	}

	public void addCoins(int coins) {
		this.coins = this.coins + coins;
	}

	public int getAssistants() {
		return assistants;
	}
	
	public boolean useAssistants(int assistantNumber) {
		if (assistants >= assistantNumber) {
			assistants = assistants - assistantNumber;
			return true;
		} else
			return false;
	}

	public void addAssistants(int assistants) {
		this.assistants = this.assistants + assistants;
	}

	public int getLevel() {
		return level;
	}

	// players can upgrade the nobility level up to infinity 
	public int upLevel() {
		level++;
		return level; // returns the new value of level to check bonuses

	}

	// double upgrade of nobility through bonus
	public void upLevel(int n) {
		level = level + n;
	}

	public int getPoints() {
		return points;
	}

	public void addPoints(int points) {
		this.points = this.points + points;
	}

	public List<PoliticCard> getHand() {
		return hand;
	}

	public int getNumberOfCards() {
		return hand.size();
	}

	public void addPolitic(PoliticCard card) {
		hand.add(card);
	}

	public boolean hasCardInHand(ColorPolitic color) {
		boolean cardInHand = false;
		for(PoliticCard card: hand) {
			if(card.getColor() == color) {
				cardInHand = true;
				break;
			}
		}
		return cardInHand;
	}
	
	// Finds a card with a specific color in the hand, returns null if there isn't one
	// TODO: eccezione se non c'è una carta nella mano?
	public PoliticCard findCardInHand(ColorPolitic color) {
		PoliticCard cardFound = null;
		for(PoliticCard card: hand) {
			if(card.getColor() == color) {
				cardFound = card;
				break;
			}
		}
		return cardFound;
	}
	
	// Removes a card from the hand and returns it
	public PoliticCard useCard(ColorPolitic color) {
		PoliticCard card = findCardInHand(color);
		if (hand.remove(card) == true) {
			return card;
		} else {
			// TODO: eccezione se non trova la carta nella mano
			return null;
			}
	}

	public void removeCard(PoliticCard card) {
		hand.remove(card);
	}
	
	public BusinessCardsPlayer getBusinessHand(){
		return businessHand;
	}
	
	public void acquireBusinessPermit(BusinessPermit permitTile) {
		businessHand.acquireBusinessPermit(permitTile);
	}

	public int getNumberOfPermits() {
		return businessHand.getNumberOfPermits();
	}

	public void sellPermits(BusinessPermit item) {
		businessHand.sellPermits(item);
	}

}